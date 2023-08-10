package com.boniecki.moviedb.usecase

import com.boniecki.moviedb.data.model.Movie
import com.boniecki.moviedb.data.store.UserDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface AddMovieToFavouritesUseCase : SingleParamUseCase<Movie, Unit>

internal class AddMovieToFavouritesUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : AddMovieToFavouritesUseCase {

    override suspend fun execute(param: Movie) {
        withContext(Dispatchers.IO) {
            val favouriteMovies = userDataStore.favouriteMovies.first() + listOf(param)
            userDataStore.saveFavouriteMovies(favouriteMovies)
        }
    }
}