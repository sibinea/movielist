package com.sibin.embibeassignment.base.dagger.module

import com.sibin.embibeassignment.appFeatures.data.repository.configuration.ConfigurationRepository
import com.sibin.embibeassignment.appFeatures.data.repository.movie.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(dataSource: MovieRepository.Network): MovieRepository = dataSource

    @Provides
    @Singleton
    fun provideConfigurationRepository(dataSource: ConfigurationRepository.Network): ConfigurationRepository = dataSource

}