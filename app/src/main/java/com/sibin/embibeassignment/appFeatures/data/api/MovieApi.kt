package com.sibin.embibeassignment.appFeatures.data.api

import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieListResponseModel
import retrofit2.Call
import retrofit2.http.*

interface MovieApi {
    companion object {
        private const val GET_NOW_PLAYING_MOVIES = "movie/now_playing"
        private const val GET_MOVIES_BY_KEYWORD = "search/movie"
    }

    @GET(GET_NOW_PLAYING_MOVIES)
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieListResponseModel>

    @GET(GET_MOVIES_BY_KEYWORD)
    fun getMoviesByKeyWord(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieListResponseModel>
}