package com.sibin.embibeassignment.appFeatures.data.repository.movie


import com.sibin.embibeassignment.BuildConfig
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieListResponseModel
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.data.functional.Either
import com.sibin.embibeassignment.base.data.network.NetworkHandler
import javax.inject.Inject

interface MovieRepository {

    fun getNowPlayingMovies(
        apiKey: String = BuildConfig.API_KEY, language: String = "en-US", page: Int = 1
    ): Either<Failure, MovieListResponseModel>

    fun getMoviesByKeyWord(
        apiKey: String = BuildConfig.API_KEY,
        query: String,
        language: String = "en-US",
        page: Int = 1
    ): Either<Failure, MovieListResponseModel>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val movieService: MovieService
    ) : MovieRepository {

        override fun getNowPlayingMovies(
            apiKey: String, language: String, page: Int
        ): Either<Failure, MovieListResponseModel> {
            return when (networkHandler.isConnected()) {
                true -> {
                    networkHandler.request(
                        movieService.getNowPlayingMovies(apiKey, language, page), {
                            it
                        }, MovieListResponseModel.empty()
                    )
                }
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getMoviesByKeyWord(
            apiKey: String, query: String, language: String, page: Int
        ): Either<Failure, MovieListResponseModel> {
            return when (networkHandler.isConnected()) {
                true -> {
                    networkHandler.request(
                        movieService.getMoviesByKeyWord(apiKey, query, language, page), {
                            it
                        }, MovieListResponseModel.empty()
                    )
                }
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}