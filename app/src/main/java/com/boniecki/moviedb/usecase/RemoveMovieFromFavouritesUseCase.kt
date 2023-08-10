package com.boniecki.moviedb.usecase

import com.boniecki.moviedb.data.model.Movie
import com.boniecki.moviedb.data.store.UserDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface RemoveMovieFromFavouritesUseCase : SingleParamUseCase<Movie, Unit>

internal class RemoveMovieFromFavouritesUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : RemoveMovieFromFavouritesUseCase {

    override suspend fun execute(param: Movie) {
        withContext(Dispatchers.IO) {
            val favouriteMovies = userDataStore.favouriteMovies.first().toMutableList().apply {
                remove(param)
            }
            userDataStore.saveFavouriteMovies(favouriteMovies)
        }
    }
}