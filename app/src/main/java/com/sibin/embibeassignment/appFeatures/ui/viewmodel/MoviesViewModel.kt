package com.sibin.embibeassignment.appFeatures.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieListResponseModel
import com.sibin.embibeassignment.appFeatures.data.repository.configuration.GetConfiguration
import com.sibin.embibeassignment.appFeatures.data.repository.movie.*
import com.sibin.embibeassignment.base.baseclass.BaseViewModel
import com.sibin.embibeassignment.base.common.SharedPreferenceKeys
import com.sibin.embibeassignment.base.common.logDebug
import com.sibin.embibeassignment.base.data.functional.UseCase
import com.sibin.embibeassignment.base.data.storage.EmbibePreferences
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val getAllMoviesFromStorage: GetAllMoviesFromStorage,
    private val addOrRemoveBookmark: AddOrRemoveBookmark,
    private val insertMoviesToStorage: InsertMoviesToStorage,
    private val preferences: EmbibePreferences,
    getConfiguration: GetConfiguration
) : BaseViewModel() {
    private var moviesList = mutableListOf<MovieDetails>()
    var movieList: MutableLiveData<List<MovieDetails>> = MutableLiveData()
    var endOfPages: MutableLiveData<Boolean> = MutableLiveData()
    private var nextPage = 1
    val imageBaseUrl =
        if (preferences.getData(SharedPreferenceKeys.IMAGE_SECURE_BASE_URL).isNullOrEmpty())
            SharedPreferenceKeys.IMAGE_SECURE_BASE_URL_DEFAULT + SharedPreferenceKeys.IMAGE_SIZE_DEFAULT
        else preferences.getData(
            SharedPreferenceKeys.IMAGE_SECURE_BASE_URL
        )
    var previousSize = 0

    init {
        logDebug("View model init")
        nextPage = preferences.getIntData(SharedPreferenceKeys.NEXT_PAGE)
        if (preferences.getBooleanData(SharedPreferenceKeys.CONFIGURATION_SYNCED)) {
            getInitialFetch()
        } else {
            getConfiguration.invoke(viewModelScope, UseCase.None()) {
                it.fold({
                    getInitialFetch()
                }, { configuration ->
                    val secureBaseUrl = configuration.images?.secure_base_url
                    val posterList = configuration.images?.poster_sizes
                    val imageSize = posterList?.get(posterList.size - 1)
                    val url = secureBaseUrl ?: SharedPreferenceKeys.IMAGE_SECURE_BASE_URL_DEFAULT
                    val size = imageSize ?: SharedPreferenceKeys.IMAGE_SIZE_DEFAULT
                    preferences.putData(SharedPreferenceKeys.CONFIGURATION_SYNCED, true)
                    preferences.putData(
                        SharedPreferenceKeys.IMAGE_SECURE_BASE_URL,
                        url + size
                    )
                    getInitialFetch()
                })
            }
        }

    }

    private fun getInitialFetch() {
        getAllMoviesFromStorage.invoke(viewModelScope, UseCase.None()) {
            it.fold(::handleFailure, ::handleMovieList)
        }
    }

    fun getMovies() {
        getMovies.invoke(viewModelScope, nextPage) {
            it.fold(::handleFailure, ::handleMovieList)
        }
    }

    /*
        Handle response from db
    */
    private fun handleMovieList(movies: List<MovieDetails>) {
        if (movies.isEmpty()) {
            getMovies()
        } else {
            movieList.value = movies.orEmpty()
        }

    }

    /*
        Handle response from api
    */
    private fun handleMovieList(movieListResponse: MovieListResponseModel) {
        nextPage++
        preferences.putData(SharedPreferenceKeys.NEXT_PAGE, nextPage)
        if (movieListResponse.results.isNotEmpty()) {
            insertMoviesToStorage.invoke(viewModelScope, movieListResponse.results)
            previousSize = moviesList.size
            moviesList.addAll(movieListResponse.results)
            movieList.value = moviesList
        }
        endOfPages.value = nextPage > movieListResponse.total_pages
    }

    fun addOrRemoveBookmark(movie: MovieDetails) {
        addOrRemoveBookmark.invoke(viewModelScope, movie) {
            it.fold(::handleFailure) { value ->
                logDebug("success $value")
            }
        }
    }

}


