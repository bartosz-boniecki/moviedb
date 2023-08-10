package com.boniecki.moviedb.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.boniecki.moviedb.R
import com.boniecki.moviedb.data.model.Movie
import com.boniecki.moviedb.databinding.ActivityMainBinding
import com.boniecki.moviedb.ui.details.MovieDetailsFragmentArgs
import com.boniecki.moviedb.ui.movies.MoviesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity :
    AppCompatActivity(),
    MoviesFragment.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onMovieClicked(movie: Movie) {
        val arguments = MovieDetailsFragmentArgs(movie = movie)
        navController.navigate(R.id.action_movies_to_movie_details, arguments.toBundle())
    }
}

