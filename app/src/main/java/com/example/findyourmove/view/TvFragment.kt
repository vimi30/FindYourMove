package com.example.findyourmove.view

import android.os.Bundle
import android.provider.SyncStateContract
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
class TvFragment : Fragment(), TVShowAdapter.OnShowClickListener, SearchAdapter.OnSearchResultItemClick{

    private lateinit var binding: FragmentTvBinding
    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var popularShowAdapter : TVShowAdapter
    private lateinit var trendingShowAdapter : TVShowAdapter
    private lateinit var topRatedShowAdapter : TVShowAdapter
    private lateinit var searchResultAdapter: SearchAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        setUpSearchResultRecyclerView()

        setUpSearchBar()

    }

    private fun setUpSearchResultRecyclerView() {

        searchResultAdapter = SearchAdapter(this,Constants.SEARCH)
        binding.searchRecyclerView.apply {
            adapter = searchResultAdapter
            layoutManager = LinearLayoutManager(context)
            //setHasFixedSize(true)
        }

        sharedViewModel.searchTvResult.observe(viewLifecycleOwner, { listOfItems ->
            searchResultAdapter.searchList = listOfItems

            Log.v("HomeSearch","search list: ${searchResultAdapter.searchList.toString()}")

        })

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
                    binding.searchRecyclerView.visibility = View.VISIBLE
                    binding.linearLayout.visibility = View.GONE
                    binding.linearLayout2.visibility = View.GONE
                    binding.linearLayout1.visibility = View.GONE

                    if (query != null) {
                        sharedViewModel.getSearchTvResults(query)
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

    override fun onTrendingShowClick(position: Int) {
        Log.d("ShowClicked", "Clicked item : ${sharedViewModel.trendingTVResponse.value?.get(position)?.name}")
        sharedViewModel.trendingTVResponse.value?.get(position)?.id?.let { sharedViewModel.getTVShowDetails(it) }
        navController.navigate(R.id.showDetailsFragment)
    }

    override fun onTopRatedShowClick(position: Int) {
        Log.d("ShowClicked", "Clicked item : ${sharedViewModel.topRatedTVResponse.value?.get(position)?.name}")
        sharedViewModel.topRatedTVResponse.value?.get(position)?.id?.let { sharedViewModel.getTVShowDetails(it) }
        navController.navigate(R.id.showDetailsFragment)
    }

    override fun onPopularShowClick(position: Int) {
        Log.d("ShowClicked", "Clicked item : ${sharedViewModel.popularTVResponse.value?.get(position)?.name}")
        sharedViewModel.popularTVResponse.value?.get(position)?.id?.let { sharedViewModel.getTVShowDetails(it) }
        navController.navigate(R.id.showDetailsFragment)
    }

    override fun onSearchItemClick(position: Int) {
        Log.d("ShowClicked", "Clicked item : ${sharedViewModel.searchTvResult.value?.get(position)?.name}")
        sharedViewModel.searchTvResult.value?.get(position)?.id?.let { sharedViewModel.getTVShowDetails(position) }
        navController.navigate(R.id.showDetailsFragment)
    }

    override fun onTrendingItemClick(position: Int) {

    }


}