package com.sibin.embibeassignment.appFeatures.data.repository.movie

import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.exception.Success
import com.sibin.embibeassignment.base.data.functional.Either
import com.sibin.embibeassignment.base.data.functional.UseCase
import javax.inject.Inject

class AddOrRemoveBookmark
@Inject constructor(
    private val movieStorageRepository: MovieStorageRepository
) :
    UseCase<Success, MovieDetails>() {
    override suspend fun run(params: MovieDetails): Either<Failure, Success> {
        return when (movieStorageRepository.getMovie(params.id)) {
            is Either.Right -> movieStorageRepository.updateMovie(params)
            is Either.Left -> movieStorageRepository.insertMovie(params)
        }
    }
}
