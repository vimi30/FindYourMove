package com.example.findyourmove.repository

import com.example.findyourmove.api.Constants
import com.example.findyourmove.api.TMDBService
import javax.inject.Inject

class MainRepository
@Inject constructor(private val tmdbService: TMDBService){

    suspend fun getPopularMovies(pageNumber: Int) =
        tmdbService.getPopularMovies(Constants.API_KEY, pageNumber)

    suspend fun getUpComingMovies(pageNumber: Int) =
            tmdbService.getUpComingMovies(Constants.API_KEY, pageNumber,"US")

    suspend fun getPopularTvShows(pageNumber: Int) =
        tmdbService.getPopularTvShows(Constants.API_KEY,pageNumber)

    suspend fun getTopRatedTvShows(pageNumber: Int) =
            tmdbService.getTopRatedTvShows(Constants.API_KEY,pageNumber)

    suspend fun getTrendingTvShows() =
            tmdbService.getTrendingTvShows(Constants.API_KEY)


    suspend fun getTrendingMovies() =
            tmdbService.getTrendingMovies(Constants.API_KEY)

    suspend fun getTrendingMedia() =
            tmdbService.getTrendingMedia(Constants.API_KEY)

    suspend fun getSearchAllResult(query:String) =
        tmdbService.getSearchResult(Constants.API_KEY,1,query)

    suspend fun getSearchMovieResult(query:String) =
        tmdbService.getMovieSearchResult(Constants.API_KEY,1,query)

    suspend fun getSearchTvResult(query:String) =
        tmdbService.getTVSearchResult(Constants.API_KEY,1,query)

    suspend fun getMovieDetails(movie_id:Int) =
            tmdbService.getMovieDetails(movie_id,Constants.API_KEY)
}