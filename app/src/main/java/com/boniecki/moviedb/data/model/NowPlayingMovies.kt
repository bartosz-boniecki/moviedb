package com.boniecki.moviedb.data.model

internal data class NowPlayingMovies(
    val page: Int,
    val results: List<Movie>
)