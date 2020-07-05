package com.sibin.embibeassignment.appFeatures.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.appFeatures.data.repository.movie.AddOrRemoveBookmark
import com.sibin.embibeassignment.appFeatures.data.repository.movie.GetBookmarkedMoviesFromStorage
import com.sibin.embibeassignment.base.baseclass.BaseViewModel
import com.sibin.embibeassignment.base.common.SharedPreferenceKeys
import com.sibin.embibeassignment.base.common.logDebug
import com.sibin.embibeassignment.base.data.functional.UseCase
import com.sibin.embibeassignment.base.data.storage.EmbibePreferences
import javax.inject.Inject

class BookmarkViewModel @Inject constructor(
    private val getBookmarkedMoviesFromStorage: GetBookmarkedMoviesFromStorage,
    private val addOrRemoveBookmark: AddOrRemoveBookmark,
    private val preferences: EmbibePreferences

) : BaseViewModel() {
    private var moviesList = mutableListOf<MovieDetails>()
    var bookmarkList: MutableLiveData<List<MovieDetails>> = MutableLiveData()
    val imageBaseUrl = preferences.getData(SharedPreferenceKeys.IMAGE_SECURE_BASE_URL)
        ?: SharedPreferenceKeys.IMAGE_SECURE_BASE_URL_DEFAULT

    fun addOrRemoveBookmark(movie: MovieDetails) {
        addOrRemoveBookmark.invoke(viewModelScope, movie) {
            it.fold(::handleFailure) { value ->
                logDebug("success $value")
            }
        }
    }

    fun getBookmarkedMovies() {
        getBookmarkedMoviesFromStorage.invoke(viewModelScope, UseCase.None()) {
            it.fold(::handleFailure, ::handleBookmarkedMovieList)
        }
    }

    private fun handleBookmarkedMovieList(movieList: List<MovieDetails>) {
        moviesList.addAll(movieList.orEmpty())
        bookmarkList.value = moviesList
    }
}


