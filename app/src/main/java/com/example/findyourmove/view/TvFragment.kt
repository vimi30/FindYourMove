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
import com.example.findyourmove.databinding.FragmentTvBinding
import com.example.findyourmove.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFragment : Fragment(), TVShowAdapter.OnShowClickListener{

    private lateinit var binding: FragmentTvBinding
    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var popularShowAdapter : TVShowAdapter
    private lateinit var trendingShowAdapter : TVShowAdapter
    private lateinit var topRatedShowAdapter : TVShowAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setUpPopularShowRecyclerView()
        setUpTopRatedShowRecyclerView()
        setUpTrendingShowRecyclerView()
        setUpSearchBar()
    }

    private fun setUpTrendingShowRecyclerView() {

        trendingShowAdapter = TVShowAdapter(this,Constants.TRENDING)
        binding.rvTrendingShows.apply {

            adapter = trendingShowAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)

        }

        sharedViewModel.trendingTVResponse.observe(viewLifecycleOwner, { listOfShows ->

            trendingShowAdapter.tvShows = listOfShows

        })

    }

    private fun setUpPopularShowRecyclerView() {

        popularShowAdapter = TVShowAdapter(this,Constants.POPULAR)
        binding.rvPopularShows.apply {

            adapter = popularShowAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)

        }

        sharedViewModel.popularTVResponse.observe(viewLifecycleOwner, { listOfShows ->

            popularShowAdapter.tvShows = listOfShows

        })

    }

    private fun setUpTopRatedShowRecyclerView() {

        topRatedShowAdapter = TVShowAdapter(this,Constants.TOP_RATED)
        binding.rvTopRatedShows.apply {

            adapter = topRatedShowAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)

        }

        sharedViewModel.topRatedTVResponse.observe(viewLifecycleOwner, { listOfShows ->

            topRatedShowAdapter.tvShows = listOfShows

        })

    }

    private fun setUpSearchBar() {

        binding.svSearchBar.apply {
            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        sharedViewModel.getSearchTvResults(query)
                        val action = TvFragmentDirections.actionTvFragmentToSearchFragment(query,Constants.TV)
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

    override fun onTrendingShowClick(position: Int) {
        Log.d("ShowClicked", "Clicked item : ${sharedViewModel.trendingTVResponse.value?.get(position)?.name}")
        sharedViewModel.trendingTVResponse.value?.get(position)?.id?.let {
            sharedViewModel.getTVShowDetails(it)
            sharedViewModel.getTVVideo(it)
        }
        navController.navigate(R.id.showDetailsFragment)
    }

    override fun onTopRatedShowClick(position: Int) {
        Log.d("ShowClicked", "Clicked item : ${sharedViewModel.topRatedTVResponse.value?.get(position)?.name}")
        sharedViewModel.topRatedTVResponse.value?.get(position)?.id?.let {
            sharedViewModel.getTVShowDetails(it)
            sharedViewModel.getTVVideo(it)
        }
        navController.navigate(R.id.showDetailsFragment)
    }

    override fun onPopularShowClick(position: Int) {
        Log.d("ShowClicked", "Clicked item : ${sharedViewModel.popularTVResponse.value?.get(position)?.name}")
        sharedViewModel.popularTVResponse.value?.get(position)?.id?.let {
            sharedViewModel.getTVShowDetails(it)
            sharedViewModel.getTVVideo(it)
        }
        navController.navigate(R.id.showDetailsFragment)
    }
}