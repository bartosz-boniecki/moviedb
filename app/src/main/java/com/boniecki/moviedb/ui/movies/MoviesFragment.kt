package com.boniecki.moviedb.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.boniecki.moviedb.R
import com.boniecki.moviedb.data.model.Movie
import com.boniecki.moviedb.databinding.FragmentMoviesBinding
import com.boniecki.moviedb.extension.requireListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class MoviesFragment : Fragment() {

    interface Listener {
        fun onMovieClicked(movie: Movie)
    }

    private val viewModel by viewModels<MoviesViewModel>()

    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMoviesBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.app_name)
        binding.somehtingWentWrongBackground.retryButton.setOnClickListener {
            viewModel.onRetryAction()
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    MoviesViewModel.MoviesUiState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.somehtingWentWrongBackground.root.isGone = true
                    }

                    MoviesViewModel.MoviesUiState.Error -> {
                        binding.progressBar.isGone = true
                        binding.somehtingWentWrongBackground.root.isVisible = true
                    }

                    is MoviesViewModel.MoviesUiState.Success -> {
                        binding.progressBar.isGone = true
                        binding.somehtingWentWrongBackground.root.isGone = true
                        setupMovies(state.movies, state.favouriteMovies)
                    }
                }
            }
        }
        viewModel.refreshFavouriteMovies()
    }

    private fun setupMovies(movies: List<Movie>, favouriteMovies: List<Movie>) {
        binding.moviesList.adapter = MoviesAdapter(
            movies = movies,
            favouriteMovies = favouriteMovies,
            actionListener = object : MoviesAdapter.Listener {
                override fun onMovieClicked(movie: Movie) {
                    requireListener<Listener>().onMovieClicked(movie)
                }

                override fun onAddMovieToFavouritesAction(movie: Movie) {
                    viewModel.onAddMovieToFavouritesAction(movie)
                }

                override fun onRemoveMovieFromFavouritesAction(movie: Movie) {
                    viewModel.onRemoveMovieFromFavouritesAction(movie)
                }
            }
        )
    }
}