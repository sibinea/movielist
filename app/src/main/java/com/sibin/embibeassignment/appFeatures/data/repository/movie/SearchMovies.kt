package com.sibin.embibeassignment.appFeatures.data.repository.movie

import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieListResponseModel
import com.sibin.embibeassignment.appFeatures.data.model.movie.SearchRequest
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.data.functional.Either
import com.sibin.embibeassignment.base.data.functional.UseCase
import javax.inject.Inject

class SearchMovies
@Inject constructor(
    private val movieRepository: MovieRepository
) :
    UseCase<MovieListResponseModel, SearchRequest>() {
    override suspend fun run(params: SearchRequest): Either<Failure, MovieListResponseModel> {
        return movieRepository.getMoviesByKeyWord(query = params.keyword,page = params.page)
    }
}
