package com.sibin.embibeassignment.appFeatures.data.dao

import androidx.room.*
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails

@Dao
interface MovieDao {
    companion object {
        private const val GET_MOVIE = "SELECT * FROM MOVIE_DETAILS WHERE  id = :id"
        private const val GET_BOOK_MARKED_MOVIES =
            "SELECT * FROM MOVIE_DETAILS WHERE isBookmarked = 1"
        private const val GET_ALL_MOVIES = "SELECT * FROM MOVIE_DETAILS "
        private const val UPDATE_BOOkMARK = "SELECT * FROM MOVIE_DETAILS "
        private const val DELETE_MOVIE = "DELETE FROM MOVIE_DETAILS WHERE id = :id"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieDetails)

    @Update()
    suspend fun update(movie: MovieDetails)

    @Transaction
    suspend fun insertAll(movies: List<MovieDetails>) {
        movies.forEach {
            insert(it)
        }
    }

    @Query(GET_ALL_MOVIES)
    suspend fun getAllMovies(): List<MovieDetails>


    @Query(GET_BOOK_MARKED_MOVIES)
    suspend fun getBookMarkedList(): List<MovieDetails>

    @Query(GET_MOVIE)
    suspend fun getMovie(id: Int): MovieDetails

    @Query(DELETE_MOVIE)
    suspend fun deleteMovie(id: Int)
}