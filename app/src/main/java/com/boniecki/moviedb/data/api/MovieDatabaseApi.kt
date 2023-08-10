package com.boniecki.moviedb.data.api

import com.boniecki.moviedb.BuildConfig
import com.boniecki.moviedb.data.model.NowPlayingMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieDatabaseApi {

    @GET("3/movie/now_playing")
    fun getNowPlayingMovies(@Query("api_key") apiKey: String = BuildConfig.MOVIES_DATABASE_API_KEY): Call<NowPlayingMovies>
}