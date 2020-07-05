package com.sibin.embibeassignment.appFeatures.data.repository.movie

import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.exception.Success
import com.sibin.embibeassignment.base.data.functional.Either
import com.sibin.embibeassignment.base.data.functional.UseCase
import javax.inject.Inject

class InsertMoviesToStorage
@Inject constructor(
    private val movieStorageRepository: MovieStorageRepository
) :
    UseCase<Success, List<MovieDetails>>() {
    override suspend fun run(params: List<MovieDetails>): Either<Failure, Success> {
        return movieStorageRepository.insertMovies(params)
    }
}
