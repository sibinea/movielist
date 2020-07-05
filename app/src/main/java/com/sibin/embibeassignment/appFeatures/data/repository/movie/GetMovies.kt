package com.sibin.embibeassignment.appFeatures.data.repository.movie

import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieListResponseModel
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.data.functional.Either
import com.sibin.embibeassignment.base.data.functional.UseCase
import javax.inject.Inject

class GetMovies
@Inject constructor(
    private val movieRepository: MovieRepository
) :
    UseCase<MovieListResponseModel, Int>() {
    override suspend fun run(params: Int): Either<Failure, MovieListResponseModel> {
        return movieRepository.getNowPlayingMovies(page = params)
    }
}
