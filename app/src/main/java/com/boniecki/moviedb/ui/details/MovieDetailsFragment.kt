package com.boniecki.moviedb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.boniecki.moviedb.R
import com.boniecki.moviedb.databinding.FragmentMovieDetailsBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<MovieDetailsViewModel>()

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentMovieDetailsBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            with(args) {
                Glide.with(requireContext()).load(args.movie.posterUrl).into(posterImageView)
                favouriteImageView.setOnClickListener {
                    viewModel.onFavouriteIconClickedAction(args.movie)
                }
                titleTextView.text = movie.title
                releaseDateTextView.text = getString(R.string.release_date_label, movie.releaseDate)
                voteTextView.text = getString(R.string.average_rating_label, movie.voteAverage)
                overviewTextView.text = movie.overview
            }
        }
        (activity as? AppCompatActivity)?.supportActionBar?.title = args.movie.title

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    MovieDetailsViewModel.MovieDetailsUiState.Error -> {
                        /** no-op **/
                    }

                    MovieDetailsViewModel.MovieDetailsUiState.Loading -> {
                        /** no-op **/
                    }

                    is MovieDetailsViewModel.MovieDetailsUiState.Success -> {
                        binding.favouriteImageView.setImageResource(
                            if (state.isFavourite) R.drawable.favorite_white_24dp
                            else R.drawable.favorite_border_white_24dp
                        )
                    }
                }
            }
        }
        viewModel.fetchMovieDetails(args.movie)
    }
}