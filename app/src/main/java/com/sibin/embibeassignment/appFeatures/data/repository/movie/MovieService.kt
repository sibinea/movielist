package com.sibin.embibeassignment.appFeatures.data.repository.movie
import com.sibin.embibeassignment.appFeatures.data.api.MovieApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieService
@Inject constructor(retrofit: Retrofit) : MovieApi {

    private val movieApi by lazy { retrofit.create(MovieApi::class.java) }

    override fun getNowPlayingMovies(
        apiKey: String,
        language: String,
        page: Int
    ) = movieApi.getNowPlayingMovies(apiKey, language, page)

    override fun getMoviesByKeyWord(
        apiKey: String,
        query: String,
        language: String,
        page: Int
    ) = movieApi.getMoviesByKeyWord(apiKey, query, language, page)
}