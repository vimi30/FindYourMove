package com.example.findyourmove.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.findyourmove.api.Constants
import com.example.findyourmove.api.TMDBService
import com.example.findyourmove.model.trendingmodel.TrendingItem
import com.example.findyourmove.model.trendingmodel.TrendingResponse
import retrofit2.Response
import java.lang.Exception

class SearchPagingDataSource(private val tmdbService: TMDBService,private val query:String,private val searchType: String) : PagingSource<Int,TrendingItem>() {



    override fun getRefreshKey(state: PagingState<Int, TrendingItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingItem> {

        val currentLoadingPageNumber = params.key?:1
        var response : Response<TrendingResponse>? = null

        when(searchType){

            Constants.ALL -> response = tmdbService.getSearchResult(Constants.API_KEY,currentLoadingPageNumber,query)
            Constants.MOVIES -> response = tmdbService.getMovieSearchResult(Constants.API_KEY,currentLoadingPageNumber,query)
            Constants.TV -> response = tmdbService.getTVSearchResult(Constants.API_KEY,currentLoadingPageNumber,query)

        }
        return try {
            val responseData = mutableListOf<TrendingItem>()
            val data = response?.body()?.trendingItems
            if (data != null) {
                responseData.addAll(data)
            }

            val prevKey = if(currentLoadingPageNumber==1) null else currentLoadingPageNumber-1

            return LoadResult.Page(
                data =  responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageNumber.plus(1)
            )


        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}