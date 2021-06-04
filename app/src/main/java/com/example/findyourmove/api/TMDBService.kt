package com.example.findyourmove.api

import androidx.annotation.AttrRes
import com.example.findyourmove.model.credit.Credits
import com.example.findyourmove.model.movie.SingleMovieModel
import com.example.findyourmove.model.moviemodels.MovieResponse
import com.example.findyourmove.model.trendingmodel.TrendingResponse
import com.example.findyourmove.model.tvshow.TVShow
import com.example.findyourmove.model.tvshowmodel.TvShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET(Constants.END_POINT_POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("api_key") api_key : String ,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET(Constants.END_POINT_UPCOMING_MOVIES)
    suspend fun getUpComingMovies(
            @Query("api_key") api_key : String ,
            @Query("page") page: Int,
            @Query("region") region : String
    ): Response<MovieResponse>


    @GET(Constants.END_POINT_TRENDING_MOVIES)
    suspend fun getTrendingMovies(
            @Query("api_key") api_key : String
    ): Response<MovieResponse>



    @GET(Constants.END_POINT_POPULAR_TV_SHOWS)
    suspend fun getPopularTvShows(
        @Query("api_key") api_key : String ,
        @Query("page") page: Int
    ): Response<TvShowResponse>


    @GET(Constants.END_POINT_TRENDING_SHOWS)
    suspend fun getTrendingTvShows(
            @Query("api_key") api_key : String
    ): Response<TvShowResponse>


    @GET(Constants.END_POINT_TOP_RATED_SHOWS)
    suspend fun getTopRatedTvShows(
            @Query("api_key") api_key : String ,
            @Query("page") page: Int,
    ): Response<TvShowResponse>

    @GET(Constants.END_POINT_TRENDING_ALL)
    suspend fun getTrendingMedia(
            @Query("api_key") api_key : String
    ): Response<TrendingResponse>


    @GET(Constants.END_POINT_SEARCH_ALL)
    suspend fun getSearchResult(
        @Query("api_key") api_key : String ,
        @Query("page") page: Int,
        @Query("query") query: String
    ):Response<TrendingResponse>


    @GET(Constants.END_POINT_SEARCH_MOVIE)
    suspend fun getMovieSearchResult(
        @Query("api_key") api_key : String ,
        @Query("page") page: Int,
        @Query("query") query: String
    ) : Response<TrendingResponse>


    @GET(Constants.END_POINT_SEARCH_TV)
    suspend fun getTVSearchResult(
        @Query("api_key") api_key : String ,
        @Query("page") page: Int,
        @Query("query") query: String
    ) : Response<TrendingResponse>

    @GET(Constants.END_POINT_MOVIE_DETAILS)
    suspend fun getMovieDetails(
            @Path("movie_id") query: Int,
            @Query("api_key") api_key : String
    )  : Response<SingleMovieModel>

    @GET(Constants.END_POINT_TV_SHOW_DETAILS)
    suspend fun getTVShowDetails(
        @Path("tv_id") query: Int,
        @Query("api_key") api_key : String
    ) : Response<TVShow>

    @GET(Constants.END_POINT_CREDITS)
    suspend fun getMovieCredits(
        @Path("movie_id") query: Int,
        @Query("api_key") api_key : String
    ) : Response<Credits>



}