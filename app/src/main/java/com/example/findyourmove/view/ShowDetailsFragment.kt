package com.example.findyourmove.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.example.findyourmove.adapters.SeasonListAdapter
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.FragmentShowDetailsBinding
import com.example.findyourmove.model.tvshow.Genre
import com.example.findyourmove.viewmodels.MainViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class ShowDetailsFragment : Fragment(){

    private lateinit var binding: FragmentShowDetailsBinding
    private val sharedViewModel:MainViewModel  by activityViewModels()
    private lateinit var navController : NavController
    private lateinit var listAdapter: SeasonListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShowDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        getDetails()
        getVideos()
    }

    private fun getDetails() {
        sharedViewModel.tvShowDetails.observe(viewLifecycleOwner, {
            Log.d("MoviesDetails", "Movie Details object: ${it.name}")
            binding.apply {
                poster.load(Constants.IMG_BASE_URL + it.posterPath) {
                    crossfade(true)
                    crossfade(1000)
                }

                listAdapter = SeasonListAdapter(it.seasons)

                seasonList.adapter = listAdapter

                tvGenre.text = getStringOfGenres(it.genres)

                ratingBar.rating = (it.voteAverage / 2).toFloat()
                tvName.text = it.name
                var year = ""
                if(!it.firstAirDate.isNullOrEmpty() && !it.lastAirDate.isNullOrEmpty()){
                    year = "${it.firstAirDate.subSequence(0,4)} - ${it.lastAirDate.subSequence(0,4)}"
                }else if(!it.firstAirDate.isNullOrEmpty() && it.lastAirDate.isNullOrEmpty()){
                    year = it.firstAirDate.subSequence(0,4).toString()
                }
                releaseYear.text = year
                tvOverview.text = it.overview
            }
        })

    }
    private fun getStringOfGenres(genres: List<Genre>): String {

        val sb = StringBuilder()

        for(i in genres.indices){
            sb.append(genres[i].name)
            if(i<genres.size-1){
                sb.append(" | ")
            }
        }
        return sb.toString()

    }

    private fun getVideos(){



        sharedViewModel.videos.observe(viewLifecycleOwner,{

//            lifecycle.addObserver(binding.trailerVideo)
            binding.trailerVideo.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {

                    if(it.results.isNotEmpty()){
                        val videoId = it.results[0].key
                        youTubePlayer.cueVideo(videoId,0f)
                    }

                }
            })

        })

    }
}