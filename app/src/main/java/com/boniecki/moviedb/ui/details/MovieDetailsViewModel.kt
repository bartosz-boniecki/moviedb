package com.boniecki.moviedb.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boniecki.moviedb.data.model.Movie
import com.boniecki.moviedb.data.store.UserDataStore
import com.boniecki.moviedb.usecase.AddMovieToFavouritesUseCase
import com.boniecki.moviedb.usecase.RemoveMovieFromFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieDetailsViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val addMovieToFavouritesUseCase: AddMovieToFavouritesUseCase,
    private val removeMovieFromFavouritesUseCase: RemoveMovieFromFavouritesUseCase
) : ViewModel() {

    sealed interface MovieDetailsUiState {
        object Loading : MovieDetailsUiState
        object Error : MovieDetailsUiState
        data class Success(val isFavourite: Boolean) : MovieDetailsUiState
    }

    private val _uiState: MutableStateFlow<MovieDetailsUiState> =
        MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)

    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    fun onFavouriteIconClickedAction(movie: Movie) {
        viewModelScope.launch {
            val isFavourite = userDataStore.favouriteMovies.first().contains(movie)
            if (isFavourite) {
                removeMovieFromFavouritesUseCase.execute(movie)
            } else {
                addMovieToFavouritesUseCase.execute(movie)
            }
        }
    }

    fun fetchMovieDetails(movie: Movie) {
        viewModelScope.launch {
            userDataStore.favouriteMovies.collect { favouriteMovies ->
                try {
                    _uiState.value =
                        MovieDetailsUiState.Success(isFavourite = favouriteMovies.contains(movie))
                } catch (e: Exception) {
                    _uiState.value = MovieDetailsUiState.Error
                }
            }
        }
    }
}