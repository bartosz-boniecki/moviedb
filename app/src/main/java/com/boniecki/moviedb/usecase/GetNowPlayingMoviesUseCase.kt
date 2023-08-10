package com.boniecki.moviedb.usecase

import com.boniecki.moviedb.data.model.Movie
import com.boniecki.moviedb.data.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface GetNowPlayingMoviesUseCase : UseCase<List<Movie>>

internal class GetNowPlayingMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetNowPlayingMoviesUseCase {

    override suspend fun execute(): List<Movie> {
        return withContext(Dispatchers.IO) {
            moviesRepository.getNowPlayingMovies()
        }
    }
}