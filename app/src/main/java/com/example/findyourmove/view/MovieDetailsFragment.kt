package com.example.findyourmove.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.findyourmove.adapters.ActorRvAdapter
import com.example.findyourmove.adapters.DirectorRvAdapter
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.FragmentMovieDetailsBinding
import com.example.findyourmove.model.credit.Crew
import com.example.findyourmove.model.movie.Genre
import com.example.findyourmove.viewmodels.MainViewModel


class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var navController: NavController
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
//        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        getDetails()
        setUpCastRv()
        setUpCrewRv()
    }

    private fun setUpCrewRv() {
        sharedViewModel.movieCrew.observe(viewLifecycleOwner,{
            binding.rvCrew.apply {

                val directors : ArrayList<Crew> = ArrayList()
                it.forEach {
                    if (it.job == "Director"){
                        directors.add(it)
                    }
                }
                adapter = DirectorRvAdapter(directors)
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            }
        })

    }

    private fun setUpCastRv() {
        sharedViewModel.movieCast.observe(viewLifecycleOwner,{
            binding.rvActors.apply {
                adapter = ActorRvAdapter(it)
                layoutManager = GridLayoutManager(context,3)
            }
        })
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


                movieGenre.text = getStringOfGenres(it.genres)
                runtime.text = it.runtime.toString()+" Min"
                ratingBar.rating = (it.voteAverage / 2).toFloat()
                movieTitle.text = it.title
                var year : String = if(!it.releaseDate.isNullOrEmpty()){
                    it.releaseDate.subSequence(0, 4).toString()
                }else{
                    ""
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


}