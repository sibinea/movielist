package com.sibin.embibeassignment.base.dagger.module

import android.content.Context
import androidx.room.Room
import com.sibin.embibeassignment.appFeatures.data.dao.MovieDao
import com.sibin.embibeassignment.appFeatures.data.repository.movie.MovieStorageRepository
import com.sibin.embibeassignment.base.data.storage.EmbibeDatabase
import com.sibin.embibeassignment.base.data.storage.EmbibePreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(dataSource: EmbibePreferences.Preferences)
            : EmbibePreferences = dataSource

    @Provides
    @Singleton
    fun provideDatabase(context: Context): EmbibeDatabase {
        return Room.databaseBuilder(
            context,
            EmbibeDatabase::class.java,
            EmbibeDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: EmbibeDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieStorageRepository(source: MovieStorageRepository.Database)
            : MovieStorageRepository = source

}

