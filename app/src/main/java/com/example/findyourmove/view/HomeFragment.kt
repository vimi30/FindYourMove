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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findyourmove.R
import com.example.findyourmove.adapters.MovieAdapter
import com.example.findyourmove.adapters.SearchAdapter
import com.example.findyourmove.adapters.TVShowAdapter
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.FragmentHomeBinding
import com.example.findyourmove.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),MovieAdapter.OnMovieClickListener,TVShowAdapter.OnShowClickListener,SearchAdapter.OnSearchResultItemClick {

    private lateinit var binding: FragmentHomeBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var popularShowAdapter: TVShowAdapter
    private lateinit var trendingAdapter: SearchAdapter
    private lateinit var navController : NavController
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setUpPopularMovieRecyclerView()

        setUpPopularShowRecyclerView()

        setUpTrendingMediaRecyclerView()

        setUpSearchBar()

        setUpSearchRv()



    }

    private fun setUpSearchRv() {

        searchAdapter = SearchAdapter(this,Constants.SEARCH)
        binding.searchRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
            //setHasFixedSize(true)
        }

        sharedViewModel.searchAllResult.observe(viewLifecycleOwner, { listOfItems ->
            searchAdapter.searchList = listOfItems

            Log.v("HomeSearch","search list: ${searchAdapter.searchList.toString()}")

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
                        sharedViewModel.getSearchAllResults(query)
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

    private fun setUpTrendingMediaRecyclerView() {

        trendingAdapter = SearchAdapter(this,Constants.TRENDING_MEDIA)
        binding.trendingRv.apply {

            adapter = trendingAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)

        }

        sharedViewModel.trendingMedia.observe(viewLifecycleOwner, { listOfMedia ->

            trendingAdapter.searchList = listOfMedia

        })

    }

    private fun setUpPopularShowRecyclerView() {

        popularShowAdapter = TVShowAdapter(this,Constants.POPULAR)
        binding.popularTvRv.apply {

            adapter = popularShowAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)

        }

        sharedViewModel.popularTVResponse.observe(viewLifecycleOwner, { listOfShows ->

            popularShowAdapter.tvShows = listOfShows

        })

    }

    private fun setUpPopularMovieRecyclerView() {

        popularMovieAdapter = MovieAdapter(this,Constants.POPULAR)
        binding.popularMovieRv.apply {
            adapter = popularMovieAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false)
            setHasFixedSize(true)
        }

        sharedViewModel.popularMovieResponse.observe(viewLifecycleOwner, { listOfMovies ->

            popularMovieAdapter.movieList = listOfMovies

//            Log.d("HomeFrag","Inside observe $listOfMovies ")

        })

    }

    override fun onPopularMovieClick(position: Int) {

        Log.d("Home Screen", "Clicked item : ${sharedViewModel.popularMovieResponse.value?.get(position)?.title}")
        sharedViewModel.popularMovieResponse.value?.get(position)?.id?.let { sharedViewModel.getMovieDetailObject(it) }
        navController.navigate(R.id.movieDetailsFragment)

    }

    override fun onTrendingMovieClick(position: Int) {

    }

    override fun onUpcomingMovieClick(position: Int) {

    }

    override fun onTrendingShowClick(position: Int) {


    }

    override fun onTopRatedShowClick(position: Int) {

    }

    override fun onPopularShowClick(position: Int) {

        Log.d("Home Screen", "Clicked item : ${sharedViewModel.popularTVResponse.value?.get(position)?.name}")
        val bundle = bundleOf("id" to sharedViewModel.popularTVResponse.value?.get(position)?.name )
        navController.navigate(R.id.showDetailsFragment,bundle)


    }

    override fun onSearchItemClick(position: Int) {

    }

    override fun onTrendingItemClick(position: Int) {

        Log.d("Home Screen", "Clicked item : ${sharedViewModel.trendingMedia.value?.get(position)?.mediaType}")

        if(sharedViewModel.trendingMedia.value?.get(position)?.mediaType == "movie"){
            val bundle = bundleOf("id" to sharedViewModel.trendingMedia.value?.get(position)?.title )
            navController.navigate(R.id.movieDetailsFragment,bundle)
        }else{
            val bundle = bundleOf("id" to sharedViewModel.trendingMedia.value?.get(position)?.name )
            navController.navigate(R.id.showDetailsFragment,bundle)
        }



    }


}