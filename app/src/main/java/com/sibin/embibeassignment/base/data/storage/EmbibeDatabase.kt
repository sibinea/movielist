package com.sibin.embibeassignment.base.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sibin.embibeassignment.appFeatures.data.dao.MovieDao
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.base.data.storage.EmbibeDatabase.Companion.DATABASE_VERSION

@Database(
    entities = [MovieDetails::class], version = DATABASE_VERSION, exportSchema = false
)
abstract class EmbibeDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "embibe_database"
        const val DATABASE_VERSION = 1
    }

    abstract fun movieDao(): MovieDao

}