package com.boniecki.moviedb.di

import com.boniecki.moviedb.usecase.AddMovieToFavouritesUseCase
import com.boniecki.moviedb.usecase.AddMovieToFavouritesUseCaseImpl
import com.boniecki.moviedb.usecase.GetFavouriteMoviesUseCase
import com.boniecki.moviedb.usecase.GetFavouriteMoviesUseCaseImpl
import com.boniecki.moviedb.usecase.GetNowPlayingMoviesUseCase
import com.boniecki.moviedb.usecase.GetNowPlayingMoviesUseCaseImpl
import com.boniecki.moviedb.usecase.RemoveMovieFromFavouritesUseCase
import com.boniecki.moviedb.usecase.RemoveMovieFromFavouritesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCaseModule {

    @Singleton
    @Binds
    fun bindsGetNowPlayingMoviesUseCase(
        getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCaseImpl
    ): GetNowPlayingMoviesUseCase

    @Singleton
    @Binds
    fun bindsGetFavouriteMoviesUseCase(
        getFavouriteMoviesUseCase: GetFavouriteMoviesUseCaseImpl
    ): GetFavouriteMoviesUseCase

    @Singleton
    @Binds
    fun bindsAddMovieToFavouritesUseCase(
        addMovieToFavouritesUseCase: AddMovieToFavouritesUseCaseImpl
    ): AddMovieToFavouritesUseCase

    @Singleton
    @Binds
    fun bindsRemoveMovieFromFavouritesUseCase(
        removeMovieFromFavouritesUseCase: RemoveMovieFromFavouritesUseCaseImpl
    ): RemoveMovieFromFavouritesUseCase
}