package com.boniecki.moviedb.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boniecki.moviedb.data.model.Movie
import com.boniecki.moviedb.usecase.AddMovieToFavouritesUseCase
import com.boniecki.moviedb.usecase.GetFavouriteMoviesUseCase
import com.boniecki.moviedb.usecase.GetNowPlayingMoviesUseCase
import com.boniecki.moviedb.usecase.RemoveMovieFromFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MoviesViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase,
    private val addMovieToFavouritesUseCase: AddMovieToFavouritesUseCase,
    private val removeMovieFromFavouritesUseCase: RemoveMovieFromFavouritesUseCase
) : ViewModel() {

    sealed interface MoviesUiState {
        object Loading : MoviesUiState
        object Error : MoviesUiState
        data class Success(
            val movies: List<Movie>,
            val favouriteMovies: List<Movie>
        ) : MoviesUiState
    }

    private val _uiState: MutableStateFlow<MoviesUiState> =
        MutableStateFlow<MoviesUiState>(MoviesUiState.Loading).also {
            fetchMovies()
        }

    val uiState: StateFlow<MoviesUiState> = _uiState

    fun onAddMovieToFavouritesAction(movie: Movie) {
        viewModelScope.launch {
            addMovieToFavouritesUseCase.execute(movie)
        }
    }

    fun onRemoveMovieFromFavouritesAction(movie: Movie) {
        viewModelScope.launch {
            removeMovieFromFavouritesUseCase.execute(movie)
        }
    }

    fun refreshFavouriteMovies() {
        val state = _uiState.value
        if (state is MoviesUiState.Success) {
            viewModelScope.launch {
                val favouriteMovies = getFavouriteMoviesUseCase.execute()
                _uiState.value = state.copy(favouriteMovies = favouriteMovies)
            }
        }
    }

    fun onRetryAction() {
        _uiState.value = MoviesUiState.Loading
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val movies = getNowPlayingMoviesUseCase.execute()
                val favouriteMovies = getFavouriteMoviesUseCase.execute()
                _uiState.value = MoviesUiState.Success(movies, favouriteMovies)
            } catch (e: Exception) {
                _uiState.value = MoviesUiState.Error
            }
        }
    }
}