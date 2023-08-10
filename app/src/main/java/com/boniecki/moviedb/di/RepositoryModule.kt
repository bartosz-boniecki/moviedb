package com.boniecki.moviedb.di

import com.boniecki.moviedb.data.repository.MoviesNetworkRepository
import com.boniecki.moviedb.data.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsMoviesRepository(
        moviesRepository: MoviesNetworkRepository
    ): MoviesRepository
}