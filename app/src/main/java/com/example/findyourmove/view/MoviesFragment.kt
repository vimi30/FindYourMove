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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findyourmove.R
import com.example.findyourmove.adapters.*
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.FragmentMoviesBinding
import com.example.findyourmove.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(), MovieAdapter.OnMovieClickListener,SearchAdapter.OnSearchResultItemClick{

    private lateinit var binding: FragmentMoviesBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var upcomingMovieAdapter: MovieAdapter
    private lateinit var trendingMovieAdapter: MovieAdapter
    private lateinit var searchResultAdapter: SearchAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setUpTrendingMovieRecyclerView()
        setUpPopularMovieRecyclerView()
        setUpUpComingMovieRecyclerView()

        setUpUpSearchResultRecyclerView()

        setUpSearchBar()

    }

    private fun setUpUpSearchResultRecyclerView() {

        searchResultAdapter = SearchAdapter(this,Constants.SEARCH)
        binding.searchRecyclerView.apply {
            adapter = searchResultAdapter
            layoutManager = LinearLayoutManager(context)
            //setHasFixedSize(true)
        }

        sharedViewModel.searchMovieResult.observe(viewLifecycleOwner, { listOfItems ->
            searchResultAdapter.searchList = listOfItems

            Log.v("HomeSearch","search list: ${searchResultAdapter.searchList.toString()}")

        })

    }

    private fun setUpUpComingMovieRecyclerView() {
        upcomingMovieAdapter = MovieAdapter(this,Constants.UPCOMING)
        binding.rvUpcomingMovies.apply {
            adapter = upcomingMovieAdapter
            layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            setHasFixedSize(true)
        }

        sharedViewModel.upComingMovieResponse.observe(viewLifecycleOwner, { listOfMovies ->

            upcomingMovieAdapter.movieList = listOfMovies

//            Log.d("MovieFrag","Upcoming:  $listOfMovies ")

        })
    }

    private fun setUpTrendingMovieRecyclerView() {

        trendingMovieAdapter = MovieAdapter(this,Constants.TRENDING)
        binding.rvTrendingMovies.apply {
            adapter = trendingMovieAdapter
            layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            setHasFixedSize(true)
        }

        sharedViewModel.trendingMovieResponse.observe(viewLifecycleOwner, { listOfMovies ->

            trendingMovieAdapter.movieList = listOfMovies

//            Log.d("MovieFrag","Trending:  $listOfMovies ")

        })

    }

    private fun setUpPopularMovieRecyclerView() {

        popularMovieAdapter = MovieAdapter(this,Constants.POPULAR)
        binding.rvPopularMovies.apply {
            adapter = popularMovieAdapter
            layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            setHasFixedSize(true)
        }

        sharedViewModel.popularMovieResponse.observe(viewLifecycleOwner, { listOfMovies ->

            popularMovieAdapter.movieList = listOfMovies

//            Log.d("MovieFrag","Popular:  $listOfMovies ")

        })

    }

    private fun setUpSearchBar() {

        binding.svSearchBar.apply {

            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchRecyclerView.visibility = View.VISIBLE
                    binding.linearLayout.visibility = View.GONE
                    binding.linearLayout2.visibility = View.GONE
                    binding.linearLayout1.visibility = View.GONE

                    if (query != null) {
                        sharedViewModel.getSearchMovieResults(query)
                    }

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

            setOnCloseListener {
                binding.searchRecyclerView.visibility = View.GONE
                binding.linearLayout.visibility = View.VISIBLE
                binding.linearLayout2.visibility = View.VISIBLE
                binding.linearLayout1.visibility = View.VISIBLE
                false
            }

        }


    }

    override fun onSearchItemClick(position: Int) {
        Log.d("Home Screen", "Clicked item : ${sharedViewModel.searchMovieResult.value?.get(position)?.title}")
        sharedViewModel.searchMovieResult.value?.get(position)?.id?.let { sharedViewModel.getMovieDetailObject(it) }
        navController.navigate(com.example.findyourmove.R.id.movieDetailsFragment)
    }

    override fun onTrendingItemClick(position: Int) {

    }

    override fun onPopularMovieClick(position: Int) {
        Log.d("Home Screen", "Clicked item : ${sharedViewModel.popularMovieResponse.value?.get(position)?.title}")
        sharedViewModel.popularMovieResponse.value?.get(position)?.id?.let { sharedViewModel.getMovieDetailObject(it) }
        navController.navigate(com.example.findyourmove.R.id.movieDetailsFragment)
    }

    override fun onTrendingMovieClick(position: Int) {
        Log.d("Home Screen", "Clicked item : ${sharedViewModel.trendingMovieResponse.value?.get(position)?.title}")
        sharedViewModel.trendingMovieResponse.value?.get(position)?.id?.let { sharedViewModel.getMovieDetailObject(it) }
        navController.navigate(com.example.findyourmove.R.id.movieDetailsFragment)
    }

    override fun onUpcomingMovieClick(position: Int) {
        Log.d("Home Screen", "Clicked item : ${sharedViewModel.upComingMovieResponse.value?.get(position)?.title}")
        sharedViewModel.upComingMovieResponse.value?.get(position)?.id?.let { sharedViewModel.getMovieDetailObject(it) }
        navController.navigate(com.example.findyourmove.R.id.movieDetailsFragment)
    }


}