package com.sibin.embibeassignment.appFeatures.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieListResponseModel
import com.sibin.embibeassignment.appFeatures.data.model.movie.SearchRequest
import com.sibin.embibeassignment.appFeatures.data.repository.movie.*
import com.sibin.embibeassignment.base.baseclass.BaseViewModel
import com.sibin.embibeassignment.base.common.SharedPreferenceKeys
import com.sibin.embibeassignment.base.common.logDebug
import com.sibin.embibeassignment.base.data.functional.UseCase
import com.sibin.embibeassignment.base.data.storage.EmbibePreferences
import com.sibin.embibeassignment.base.extension.empty
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchMovie: SearchMovies,
    private val addOrRemoveBookmark: AddOrRemoveBookmark,
    private val preferences: EmbibePreferences

) : BaseViewModel() {
    var movieList: MutableLiveData<List<MovieDetails>> = MutableLiveData()
    var searchQuery = String.empty()
    private var nextPage = 1
    val imageBaseUrl =
        if (!preferences.getData(SharedPreferenceKeys.IMAGE_SECURE_BASE_URL).isNullOrEmpty())
            preferences.getData(SharedPreferenceKeys.IMAGE_SECURE_BASE_URL)
        else SharedPreferenceKeys.IMAGE_SECURE_BASE_URL_DEFAULT + SharedPreferenceKeys.IMAGE_SIZE_DEFAULT


    fun getMovies() {
        searchMovie.invoke(viewModelScope, SearchRequest(searchQuery, nextPage)) {
            it.fold(::handleFailure, ::handleMovieList)
        }
    }

    private fun handleMovieList(movieListResponse: MovieListResponseModel) {
        movieList.value = movieListResponse.results.orEmpty()
    }

    fun addOrRemoveBookmark(movie: MovieDetails) {
        addOrRemoveBookmark.invoke(viewModelScope, movie) {
            it.fold(::handleFailure) { value ->
                logDebug("success $value")
            }
        }
    }
}


