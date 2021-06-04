package com.example.findyourmove.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findyourmove.R
import com.example.findyourmove.adapters.SearchAdapterWithPaging
import com.example.findyourmove.adapters.SearchLoadStateAdapter
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.FragmentSearchBinding
import com.example.findyourmove.model.trendingmodel.TrendingItem
import com.example.findyourmove.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), SearchAdapterWithPaging.OnSearchItemClickListener{

    private lateinit var binding: FragmentSearchBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var navController : NavController
    private lateinit var searchingFor : String
    private lateinit var mainListAdapter: SearchAdapterWithPaging




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args : SearchFragmentArgs by navArgs()
        searchingFor = args.searchingFor
        navController = Navigation.findNavController(view)

        setUpSearchBar(args)
        setUpSearchRv()

        setupView()

    }

    private fun setupView() {
        lifecycleScope.launch {
            sharedViewModel.testPagingList?.collect {
                mainListAdapter.submitData(it)

            }
        }
    }

    private fun setUpSearchBar(args: SearchFragmentArgs) {

        val query = args.query


        binding.svSearchBar.apply {
            setIconifiedByDefault(false)
            setQuery(query,false)

            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        when(searchingFor){
                            Constants.ALL -> sharedViewModel.getSearchAllResults(query)
                            Constants.MOVIES -> sharedViewModel.getSearchMovieResults(query)
                            Constants.TV -> sharedViewModel.getSearchTvResults(query)
                        }
                        setupView()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }


    }

    private fun setUpSearchRv() {

//        searchAdapter = SearchAdapter(this,Constants.SEARCH)
        mainListAdapter = SearchAdapterWithPaging(this)
        binding.rvSearchResult.apply {
            adapter = mainListAdapter.withLoadStateHeaderAndFooter(
                header = SearchLoadStateAdapter(),
                footer = SearchLoadStateAdapter()
            )
            layoutManager = LinearLayoutManager(context)
//            addOnScrollListener(object : RecyclerView.OnScrollListener(){
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
////                    if(!recyclerView.canScrollVertically(1)){
////                        sharedViewModel.getNextPageSearchResult(1)
////                    }
//                }
//            })
        }

//        when(searchingFor){
//
//            Constants.ALL -> sharedViewModel.searchAllResult.observe(viewLifecycleOwner, { listOfItems ->
//                searchAdapter.searchList = listOfItems
//            })
//
//            Constants.MOVIES -> sharedViewModel.searchMovieResult.observe(viewLifecycleOwner, { listOfItems ->
//                searchAdapter.searchList = listOfItems
//            })
//
//            Constants.TV -> sharedViewModel.searchTvResult.observe(viewLifecycleOwner, { listOfItems ->
//                searchAdapter.searchList = listOfItems
//            })
//
//        }

    }

    override fun onSearchItemClick(searchItem: TrendingItem) {

        when(searchingFor){

            Constants.ALL -> {
                if(searchItem.mediaType == "movie"){
                    sharedViewModel.getMovieDetailObject(searchItem.id)
                    sharedViewModel.getMovieCredit(searchItem.id)
                    navController.navigate(R.id.movieDetailsFragment)
                }else{
                    sharedViewModel.getTVShowDetails(searchItem.id)
                    navController.navigate(R.id.showDetailsFragment)
                }

            }
            Constants.MOVIES -> {
                sharedViewModel.getMovieDetailObject(searchItem.id)
                sharedViewModel.getMovieCredit(searchItem.id)
                navController.navigate(R.id.movieDetailsFragment)
            }
            Constants.TV ->    {
                sharedViewModel.getTVShowDetails(searchItem.id)
                navController.navigate(R.id.showDetailsFragment)
            }


        }

    }


}