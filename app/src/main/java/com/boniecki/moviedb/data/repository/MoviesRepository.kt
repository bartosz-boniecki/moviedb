package com.boniecki.moviedb.data.repository

import com.boniecki.moviedb.data.api.MovieDatabaseApi
import com.boniecki.moviedb.data.model.Movie
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

internal interface MoviesRepository {
    suspend fun getNowPlayingMovies(): List<Movie>
}

internal class MoviesNetworkRepository @Inject constructor(private val api: MovieDatabaseApi) :
    MoviesRepository {

    override suspend fun getNowPlayingMovies(): List<Movie> {
        val response = api.getNowPlayingMovies().execute()
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            Timber.w("Failed to fetch now playing movies: ${response.errorBody()}")
            throw HttpException(response)
        }
    }
}