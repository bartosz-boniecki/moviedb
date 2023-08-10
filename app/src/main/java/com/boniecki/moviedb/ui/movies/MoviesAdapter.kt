package com.boniecki.moviedb.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boniecki.moviedb.R
import com.boniecki.moviedb.data.model.Movie
import com.boniecki.moviedb.databinding.MoviesListItemBinding
import com.bumptech.glide.Glide

internal class MoviesAdapter(
    private val movies: List<Movie>,
    favouriteMovies: List<Movie>,
    private val actionListener: Listener
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val favouriteMovies: MutableList<Movie> = favouriteMovies.toMutableList()

    interface Listener {
        fun onMovieClicked(movie: Movie)
        fun onAddMovieToFavouritesAction(movie: Movie)
        fun onRemoveMovieFromFavouritesAction(movie: Movie)
    }

    inner class ViewHolder(binding: MoviesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val posterImageView: ImageView = binding.posterImageView
        val titleTextView: TextView = binding.title
        val favouriteImageView: ImageView = binding.favouriteImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            MoviesListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        with(holder) {
            Glide
                .with(posterImageView)
                .load(movie.posterUrl)
                .centerCrop()
                .into(posterImageView)
            titleTextView.text = movie.title
            favouriteImageView.setImageResource(
                if (favouriteMovies.contains(movie)) R.drawable.favorite_white_24dp
                else R.drawable.favorite_border_white_24dp
            )
            favouriteImageView.setOnClickListener {
                if (favouriteMovies.contains(movie)) {
                    favouriteMovies.remove(movie)
                    actionListener.onRemoveMovieFromFavouritesAction(movie)
                } else {
                    favouriteMovies.add(movie)
                    actionListener.onAddMovieToFavouritesAction(movie)
                }
                notifyItemChanged(position)
            }
            itemView.setOnClickListener {
                actionListener.onMovieClicked(movie)
            }
        }
    }

    override fun getItemCount() = movies.size
}