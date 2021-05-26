package com.example.findyourmove.view

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.example.findyourmove.adapters.MovieAdapter
import com.example.findyourmove.adapters.SearchAdapter
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.FragmentMovieDetailsBinding
import com.example.findyourmove.viewmodels.MainViewModel


class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var navController: NavController
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
//        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        getDetails()
    }


    private fun getDetails() {
        sharedViewModel.movieDetails.observe(viewLifecycleOwner, {

            Log.d("MoviesDetails", "Movie Details object: ${it.title}")

            binding.apply {
                poster.load(Constants.IMG_BASE_URL + it.posterPath) {
                    crossfade(true)
                    crossfade(1000)
                }
                backdrop.load(Constants.IMG_BASE_URL + it.backdropPath) {
                    crossfade(true)
                    crossfade(1000)
                }

                movieGenre.text = it.genres[0].name
                ratingBar.rating = (it.voteAverage / 2).toFloat()
                movieTitle.text = it.title
                releaseYear.text = it.releaseDate.subSequence(0, 4)
                tvOverview.text = it.overview
            }
        })

    }





}