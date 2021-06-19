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
import com.example.findyourmove.adapters.MovieAdapter
import com.example.findyourmove.adapters.TrendingAllAdapter
import com.example.findyourmove.adapters.TVShowAdapter
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.FragmentHomeBinding
import com.example.findyourmove.databinding.SnackbarLayoutBinding
import com.example.findyourmove.model.trendingmodel.TrendingItem
import com.example.findyourmove.utils.ConnectionStatus
import com.example.findyourmove.viewmodels.MainViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),MovieAdapter.OnMovieClickListener,TVShowAdapter.OnShowClickListener,TrendingAllAdapter.OnSearchResultItemClick {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var connectionStatus: ConnectionStatus
    private lateinit var snackbar: SnackbarLayoutBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var popularShowAdapter: TVShowAdapter
    private lateinit var trendingAdapter: TrendingAllAdapter
    private lateinit var navController : NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        snackbar = SnackbarLayoutBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        connectionStatus = ConnectionStatus(requireContext())

        connectionStatus.observe(viewLifecycleOwner,{ isNetWorkAvailable ->

            if(isNetWorkAvailable!=null && isNetWorkAvailable){
                Log.d("Home Connection", "Connection Available: $isNetWorkAvailable")
                
            }else{
                Log.d("Home Connection", "Connection Available: $isNetWorkAvailable")
                snackbar.tvSnakebar.text = "No Internet Connection"
                Snackbar.make(view ,"No Internet Connection",Snackbar.LENGTH_SHORT)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show()
            }

        })

        setUpPopularMovieRecyclerView()

        setUpPopularShowRecyclerView()

        setUpTrendingMediaRecyclerView()

        setUpSearchBar()

    }


    private fun setUpSearchBar() {

        binding.svSearchBar.apply {

            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        sharedViewModel.getSearchAllResults(query)
                        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(query,Constants.ALL)
                        navController.navigate(action)
                    }


                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }


    }

    private fun setUpTrendingMediaRecyclerView() {

        trendingAdapter = TrendingAllAdapter(this,Constants.TRENDING_MEDIA)
        binding.trendingRv.apply {

            adapter = trendingAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)

        }

        sharedViewModel.trendingMedia.observe(viewLifecycleOwner, { listOfMedia ->

            trendingAdapter.searchList = listOfMedia as MutableList<TrendingItem>

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
        sharedViewModel.popularMovieResponse.value?.get(position)?.id?.let {
            sharedViewModel.getMovieDetailObject(it)
            sharedViewModel.getMovieCredit(it)
            sharedViewModel.getVideo(it)
        }
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
        sharedViewModel.popularTVResponse.value?.get(position)?.id?.let {
            sharedViewModel.getTVShowDetails(it)
            sharedViewModel.getTVVideo(it)
        }
        navController.navigate(R.id.showDetailsFragment)


    }

    override fun onSearchItemClick(position: Int) {

        Log.d("Home Screen", "Clicked item : ${sharedViewModel.searchAllResult.value?.get(position)?.mediaType}")

        if(sharedViewModel.trendingMedia.value?.get(position)?.mediaType == "movie"){
            Log.d("Home Screen", "Clicked movie item : ${sharedViewModel.searchAllResult.value?.get(position)?.title}")
            sharedViewModel.searchAllResult.value?.get(position)?.id?.let {
                sharedViewModel.getMovieDetailObject(it)
                sharedViewModel.getMovieCredit(it)
                sharedViewModel.getVideo(it)
            }
            navController.navigate(R.id.movieDetailsFragment)
        }else{
            Log.d("Home Screen", "Clicked TV item : ${sharedViewModel.searchAllResult.value?.get(position)?.name}")
            sharedViewModel.searchAllResult.value?.get(position)?.id?.let {
                sharedViewModel.getTVShowDetails(it)
                sharedViewModel.getTVVideo(it)
            }
            navController.navigate(R.id.showDetailsFragment)
        }

    }

    override fun onTrendingItemClick(position: Int) {

        Log.d("Home Screen", "Clicked item : ${sharedViewModel.trendingMedia.value?.get(position)?.mediaType}")

        if(sharedViewModel.trendingMedia.value?.get(position)?.mediaType == "movie"){
            Log.d("Home Screen", "Clicked item : ${sharedViewModel.trendingMedia.value?.get(position)?.title}")
            sharedViewModel.trendingMedia.value?.get(position)?.id?.let {
                sharedViewModel.getMovieDetailObject(it)
                sharedViewModel.getMovieCredit(it)
                sharedViewModel.getVideo(it)
            }
            navController.navigate(R.id.movieDetailsFragment)
        }else{
            Log.d("Home Screen", "Clicked item : ${sharedViewModel.trendingMedia.value?.get(position)?.name}")
            sharedViewModel.trendingMedia.value?.get(position)?.id?.let {
                sharedViewModel.getTVShowDetails(it)
                sharedViewModel.getTVVideo(it)
            }
            navController.navigate(R.id.showDetailsFragment)
        }



    }


}