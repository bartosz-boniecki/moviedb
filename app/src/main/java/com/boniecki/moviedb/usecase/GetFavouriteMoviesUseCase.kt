package com.boniecki.moviedb.usecase

import com.boniecki.moviedb.data.model.Movie
import com.boniecki.moviedb.data.store.UserDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface GetFavouriteMoviesUseCase : UseCase<List<Movie>>

internal class GetFavouriteMoviesUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : GetFavouriteMoviesUseCase {
    override suspend fun execute(): List<Movie> {
        return withContext(Dispatchers.IO) {
            userDataStore.favouriteMovies.first()
        }
    }
}