package com.sibin.embibeassignment.appFeatures.data.repository.movie

import com.sibin.embibeassignment.appFeatures.data.dao.MovieDao
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.exception.StorageFailure
import com.sibin.embibeassignment.base.exception.Success
import com.sibin.embibeassignment.base.data.functional.Either
import javax.inject.Inject

interface MovieStorageRepository {

    suspend fun insertMovie(movie: MovieDetails): Either<Failure, Success>
    suspend fun updateMovie(movie: MovieDetails): Either<Failure, Success>
    suspend fun insertMovies(movies: List<MovieDetails>): Either<Failure, Success>
    suspend fun getAllMovies(): Either<Failure, List<MovieDetails>>
    suspend fun getBookMarkedMovies(): Either<Failure, List<MovieDetails>>
    suspend fun getMovie(id: Int): Either<Failure, MovieDetails>
    suspend fun deleteMovie(id: Int): Either<Failure, Success>

    class Database
    @Inject constructor(private val movieDao: MovieDao) :
        MovieStorageRepository {
        override suspend fun insertMovies(movies: List<MovieDetails>): Either<Failure, Success> {
            movieDao.insertAll(movies)
            return Either.Right(Success.StorageSuccess)
        }

        override suspend fun insertMovie(movie: MovieDetails): Either<Failure, Success> {
            movieDao.insert(movie)
            return Either.Right(Success.StorageSuccess)
        }

        override suspend fun updateMovie(movie: MovieDetails): Either<Failure, Success> {
            movieDao.update(movie)
            return Either.Right(Success.StorageSuccess)
        }

        override suspend fun getAllMovies(): Either<Failure, List<MovieDetails>> {
            return Either.Right(movieDao.getAllMovies().orEmpty())
        }

        override suspend fun getBookMarkedMovies(): Either<Failure, List<MovieDetails>> {
            return Either.Right(movieDao.getBookMarkedList().orEmpty())
        }

        override suspend fun getMovie(id: Int): Either<Failure, MovieDetails> {
            val movie = movieDao.getMovie(id)
            return if (movie != null) {
                Either.Right(movie)
            } else {
                Either.Left(StorageFailure())
            }
        }

        override suspend fun deleteMovie(id: Int): Either<Failure, Success> {
            movieDao.deleteMovie(id)
            return Either.Right(Success.StorageSuccess)
        }
    }
}