package com.sibin.embibeassignment.appFeatures.data.repository.movie

import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.base.data.functional.UseCase
import javax.inject.Inject

class GetAllMoviesFromStorage
@Inject constructor(private val movieStorageRepository: MovieStorageRepository) :
    UseCase<List<MovieDetails>, UseCase.None>() {
    override suspend fun run(params: None) = movieStorageRepository.getAllMovies()
}