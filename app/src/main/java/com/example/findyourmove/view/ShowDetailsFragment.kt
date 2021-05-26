package com.example.findyourmove.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.example.findyourmove.R
import com.example.findyourmove.adapters.TVShowAdapter
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.FragmentShowDetailsBinding
import com.example.findyourmove.viewmodels.MainViewModel

class ShowDetailsFragment : Fragment(){

    private lateinit var binding: FragmentShowDetailsBinding
    private val sharedViewModel:MainViewModel  by activityViewModels()
    private lateinit var navController : NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        getDetails()
    }

    private fun getDetails() {
        sharedViewModel.tvShowDetails.observe(viewLifecycleOwner, {

            Log.d("MoviesDetails", "Movie Details object: ${it.name}")

            binding.apply {
                poster.load(Constants.IMG_BASE_URL + it.posterPath) {
                    crossfade(true)
                    crossfade(1000)
                }
                backdrop.load(Constants.IMG_BASE_URL + it.backdropPath) {
                    crossfade(true)
                    crossfade(1000)
                }

                if(it.genres.isNotEmpty()){
                    tvGenre.text = it.genres[0].name
                }

                ratingBar.rating = (it.voteAverage / 2).toFloat()
                tvName.text = it.name
                var year = "${it.firstAirDate.subSequence(0,4)} - ${it.lastAirDate.subSequence(0,4)}"
                releaseYear.text = year
                tvOverview.text = it.overview
            }
        })

    }
}