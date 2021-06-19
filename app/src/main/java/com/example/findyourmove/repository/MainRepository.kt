package com.example.findyourmove.repository

import com.example.findyourmove.api.Constants
import com.example.findyourmove.api.TMDBService
import com.example.findyourmove.model.credit.Credits
import com.example.findyourmove.model.video.Videos
import retrofit2.Response
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

//    suspend fun getSearchAllResult(query:String,pageNumber:Int) =
//        tmdbService.getSearchResult(Constants.API_KEY,pageNumber,query)

    suspend fun getSearchMovieResult(query:String,pageNumber:Int) =
        tmdbService.getMovieSearchResult(Constants.API_KEY,pageNumber,query)

//    suspend fun getSearchTvResult(query:String,pageNumber: Int) =
//        tmdbService.getTVSearchResult(Constants.API_KEY,pageNumber,query)

    suspend fun getMovieDetails(movie_id:Int) =
            tmdbService.getMovieDetails(movie_id,Constants.API_KEY)

    suspend fun getTVShowDetails(tv_id:Int) =
        tmdbService.getTVShowDetails(tv_id,Constants.API_KEY)

    suspend fun getMovieCredits(movie_id: Int): Response<Credits> {
        return tmdbService.getMovieCredits(movie_id,Constants.API_KEY)
    }

    suspend fun getMovieVideo(movie_id: Int):Response<Videos>{
        return tmdbService.getMovieVideos(movie_id,Constants.API_KEY)
    }

    suspend fun getTVVideo(tv_id: Int):Response<Videos>{
        return tmdbService.getTVVideos(tv_id,Constants.API_KEY)
    }
}