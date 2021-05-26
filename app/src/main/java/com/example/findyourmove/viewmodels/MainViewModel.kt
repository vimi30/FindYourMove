package com.example.findyourmove.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findyourmove.model.movie.SingleMovieModel
import com.example.findyourmove.model.moviemodels.MovieResponseItem
import com.example.findyourmove.model.trendingmodel.TrendingItem
import com.example.findyourmove.model.tvshow.TVShow
import com.example.findyourmove.model.tvshowmodel.TVShowResponseItem
import com.example.findyourmove.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject
constructor(private val repository: MainRepository) : ViewModel(){

    private val _popularMovieResponse = MutableLiveData<List<MovieResponseItem>>()
    val popularMovieResponse : LiveData<List<MovieResponseItem>>
        get() = _popularMovieResponse

    private val _trendingMovieResponse = MutableLiveData<List<MovieResponseItem>>()
    val trendingMovieResponse : LiveData<List<MovieResponseItem>>
        get() = _trendingMovieResponse

    private val _upComingMovieResponse = MutableLiveData<List<MovieResponseItem>>()
    val upComingMovieResponse : LiveData<List<MovieResponseItem>>
        get() = _upComingMovieResponse



    private val _popularTVResponse = MutableLiveData<List<TVShowResponseItem>>()
    val popularTVResponse : LiveData<List<TVShowResponseItem>>
        get() = _popularTVResponse

    private val _topRatedTVResponse = MutableLiveData<List<TVShowResponseItem>>()
    val topRatedTVResponse : LiveData<List<TVShowResponseItem>>
        get() = _topRatedTVResponse

    private val _trendingTVResponse = MutableLiveData<List<TVShowResponseItem>>()
    val trendingTVResponse : LiveData<List<TVShowResponseItem>>
        get() = _trendingTVResponse


    private val _trendingMedia = MutableLiveData<List<TrendingItem>>()
    val trendingMedia : LiveData<List<TrendingItem>>
        get() = _trendingMedia

    private val _searchAllResults = MutableLiveData<List<TrendingItem>>()
    val searchAllResult : LiveData<List<TrendingItem>>
        get() = _searchAllResults

    private val _searchMovieResults = MutableLiveData<List<TrendingItem>>()
    val searchMovieResult : LiveData<List<TrendingItem>>
        get() = _searchMovieResults

    private val _searchTvResults = MutableLiveData<List<TrendingItem>>()
    val searchTvResult : LiveData<List<TrendingItem>>
        get() = _searchTvResults

    private val _movieDetails = MutableLiveData<SingleMovieModel>()
    val movieDetails : LiveData<SingleMovieModel>
        get() = _movieDetails

    private val _tvShowDetails = MutableLiveData<TVShow>()
    val tvShowDetails : LiveData<TVShow>
        get() = _tvShowDetails






    init {

        //Log.d("ViewModel", "INIT called")
        getTrendingMedia()
        getPopularMovies()
        getUpComingMovies()
        getTrendingMovies()
        getPopularTVShows()
        getTopRatedTVShows()
        getTrendingTVShows()


    }

    fun getTVShowDetails(tv_id:Int){
        viewModelScope.launch {
            repository.getTVShowDetails(tv_id)
                .let { response ->

                    Log.d("TVShowDetails","TVShowDetailsResponse: ${response.raw().request.url}")
                    if(response.isSuccessful)  {
                        _tvShowDetails.postValue(response.body())
                        Log.d("TVShowDetails","TVShowDetailsResponse: ${response.body()?.name}")
                    } else{
                        Log.d("TVShowDetails","TVShowDetailsResponse: ${response.errorBody().toString()}")
                    }

                }
        }
    }

    fun getMovieDetailObject(movie_id:Int) {
        viewModelScope.launch {
            repository.getMovieDetails(movie_id)
                    .let { response ->

                        Log.d("MovieDetails","MovieDetailResponse: ${response.raw().request.url}")
                    if(response.isSuccessful)  {
                        _movieDetails.postValue(response.body())
                        Log.d("MovieDetails","MovieDetailResponse: ${response.body()?.title}")
                    } else{
                        Log.d("MovieDetails","MovieDetailResponse: ${response.errorBody().toString()}")
                    }

                    }
        }
    }

    fun getSearchAllResults( query:String ) {

        viewModelScope.launch {

            repository.getSearchAllResult(query)
                .let { response ->

                    if(response.isSuccessful){
                        _searchAllResults.postValue(response.body()?.trendingItems)
                        Log.d("TMDBResponse","SearchResponse: ${response.body()?.trendingItems.toString()}")
                    }else{
                        Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
                    }

                }

        }

    }

    fun getSearchMovieResults( query:String ) {

        viewModelScope.launch {

            repository.getSearchMovieResult(query)
                .let { response ->

                    if(response.isSuccessful){
                        _searchMovieResults.postValue(response.body()?.trendingItems)
                        Log.d("TMDBResponse","SearchResponse: ${response.body()?.trendingItems.toString()}")
                    }else{
                        Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
                    }

                }

        }

    }

    fun getSearchTvResults( query:String ) {

        viewModelScope.launch {

            repository.getSearchTvResult(query)
                .let { response ->

                    if(response.isSuccessful){
                        _searchTvResults.postValue(response.body()?.trendingItems)
                        Log.d("TMDBResponse","SearchResponse: ${response.body()?.trendingItems.toString()}")
                    }else{
                        Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
                    }

                }

        }

    }

    private fun getTrendingMedia() {

        viewModelScope.launch {

            repository.getTrendingMedia()
                    .let { response ->
                        if (response.isSuccessful){
                            _trendingMedia.postValue(response.body()?.trendingItems)
                        }else{
                            Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
                        }
                    }
        }

    }

    private fun getTrendingTVShows() {

        viewModelScope.launch {

            repository.getTrendingTvShows()
                    .let { response ->
                        if (response.isSuccessful){
                            _trendingTVResponse.postValue(response.body()?.TVShowResponseItems)
                        }else{
                            Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
                        }
                    }
        }

    }

    private fun getTopRatedTVShows() {

        viewModelScope.launch {

            repository.getTopRatedTvShows(1)
                    .let { response ->
                        if (response.isSuccessful){
                            _topRatedTVResponse.postValue(response.body()?.TVShowResponseItems)
                        }else{
                            Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
                        }
                    }
        }

    }

    private fun getUpComingMovies() {

        viewModelScope.launch {

            repository.getUpComingMovies(1)
                    .let { response ->

                        if(response.isSuccessful){

                            _upComingMovieResponse.postValue(response.body()?.movieResponseItems)
                        }else{
                            Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
                        }

                    }

        }

    }

    private fun getTrendingMovies() {
        viewModelScope.launch {

            repository.getTrendingMovies()
                    .let { response ->

                        if(response.isSuccessful){
                            _trendingMovieResponse.postValue(response.body()?.movieResponseItems)
                        }else{
                            Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
                        }

                    }

        }
    }

    private fun getPopularTVShows() {

        viewModelScope.launch {

            repository.getPopularTvShows(1)
                .let { response ->

                    if (response.isSuccessful){
                        _popularTVResponse.postValue(response.body()?.TVShowResponseItems)

                        response.body()?.TVShowResponseItems?.forEach { it ->
                            Log.d("TMDBResponse",it.name)
                        }
                    }else{
                        Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
                    }
                }


        }


    }

    private fun getPopularMovies() {
        viewModelScope.launch {

            Log.d("ViewModel", "inside ViewModelScope")

            repository.getPopularMovies(1)
                .let { response ->

                    //Log.d("ViewModel", "Inside Response")

                    if (response.isSuccessful){
                        _popularMovieResponse.postValue(response.body()?.movieResponseItems)

                    }else{
                        Log.d("TMDBResponse","error : ${response.errorBody().toString()}")
                    }


                }

        }
    }



}