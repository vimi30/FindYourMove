package com.example.findyourmove.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.findyourmove.adapters.SearchPagingDataSource
import com.example.findyourmove.api.Constants
import com.example.findyourmove.api.TMDBService
import com.example.findyourmove.model.credit.Cast
import com.example.findyourmove.model.credit.Crew
import com.example.findyourmove.model.movie.SingleMovieModel
import com.example.findyourmove.model.moviemodels.MovieResponseItem
import com.example.findyourmove.model.trendingmodel.TrendingItem
import com.example.findyourmove.model.tvshow.TVShow
import com.example.findyourmove.model.tvshowmodel.TVShowResponseItem
import com.example.findyourmove.model.video.Videos
import com.example.findyourmove.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject
constructor(private val repository: MainRepository) : ViewModel(){

    @Inject lateinit var tmdbService: TMDBService
    private var searchPageNumber : Int = 0
    lateinit var searchAllQuery : String

    var testPagingList : Flow<PagingData<TrendingItem>>? = null

    private val _popularMovieResponse = MutableLiveData<List<MovieResponseItem>>()
    val popularMovieResponse : LiveData<List<MovieResponseItem>>
        get() = _popularMovieResponse

    private val _trendingMovieResponse = MutableLiveData<List<MovieResponseItem>>()
    val trendingMovieResponse : LiveData<List<MovieResponseItem>>
        get() = _trendingMovieResponse

    private val _upComingMovieResponse = MutableLiveData<List<MovieResponseItem>>()
    val upComingMovieResponse : LiveData<List<MovieResponseItem>>
        get() = _upComingMovieResponse

    private val _movieCast = MutableLiveData<List<Cast>>()
    val movieCast: LiveData<List<Cast>>
        get() = _movieCast

    private val _movieCrew = MutableLiveData<List<Crew>>()
    val movieCrew: LiveData<List<Crew>>
        get() = _movieCrew



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


    private val _searchAllResults = MutableLiveData<MutableList<TrendingItem>>()
    val searchAllResult : LiveData<MutableList<TrendingItem>>
        get() = _searchAllResults

    private val _searchMovieResults = MutableLiveData<MutableList<TrendingItem>>()
    val searchMovieResult : LiveData<MutableList<TrendingItem>>
        get() = _searchMovieResults

    private val _searchTvResults = MutableLiveData<MutableList<TrendingItem>>()
    val searchTvResult : LiveData<MutableList<TrendingItem>>
        get() = _searchTvResults

    private val _movieDetails = MutableLiveData<SingleMovieModel>()
    val movieDetails : LiveData<SingleMovieModel>
        get() = _movieDetails

    private val _tvShowDetails = MutableLiveData<TVShow>()
    val tvShowDetails : LiveData<TVShow>
        get() = _tvShowDetails

    private val _videos = MutableLiveData<Videos>()
    val videos : LiveData<Videos>
        get() = _videos






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
                        Log.e("TVShowDetails","TVShowDetailsResponse: ${response.errorBody().toString()}")
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

    fun getVideo(movie_id: Int){
        viewModelScope.launch {
            repository.getMovieVideo(movie_id)
                .let { response ->

                    if(response.isSuccessful){
                        _videos.postValue(response.body())
                    }else{
                        Log.e("error","Error while getting the videos: ${response.errorBody().toString()}")
                    }
                }
        }
    }

    fun getTVVideo(tv_id: Int){
        viewModelScope.launch {
            repository.getTVVideo(tv_id)
                .let { response ->

                    if(response.isSuccessful){
                        _videos.postValue(response.body())
                    }else{
                        Log.e("error","Error while getting the videos: ${response.errorBody().toString()}")
                    }
                }
        }
    }

//    fun getSearchAllResults( query:String,pageNumber:Int ) {
//
//        searchAllQuery = query
//        searchPageNumber = pageNumber
//
//        Log.d("TMDBResponse","Search request for page number $pageNumber")
//
//        viewModelScope.launch {
//
//            repository.getSearchAllResult(query,pageNumber)
//                .let { response ->
//
//                    Log.d("TMDBResponse","Search URL: ${response.raw().request.url}")
//                    if(response.isSuccessful){
////                        val list  = response.body()?.trendingItems
//                        if(pageNumber == 1){
//                            _searchAllResults.postValue(response.body()?.trendingItems)
//                        }else{
//                            val oldPage = _searchAllResults.value
//                            val newPage = response.body()?.trendingItems
//                            if (newPage != null) {
//                                oldPage?.addAll(newPage)
//                            }
//
//                            Log.d("TMDBResponse","OLD_PAGE: Size: ${oldPage?.size}, value: ${oldPage.toString()}")
//
//                            _searchAllResults.postValue(newPage)
////
//                        }
//
//                        Log.d("TMDBResponse","SearchResponse size: ${_searchAllResults.value?.size}")
//                    }else{
//                        Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
//                    }
//
//                }
//
//        }
//
//    }

    fun getMovieCredit(movie_id: Int){
        viewModelScope.launch {

            repository.getMovieCredits(movie_id)
                .let { response ->

                if(response.isSuccessful){
                    _movieCast.postValue(response.body()?.cast)
                    _movieCrew.postValue(response.body()?.crew)
                    Log.d("TMDBResponse","SearchResponse: ${response.body()?.cast.toString()}")
                }else{
                    Log.d("TMDBResponse","SearchResponse: ${response.errorBody()}")
                }


                }

        }
    }

//    fun getNextPageSearchResult(option: Int){
//        searchPageNumber += 1
//        when(option){
//            1->getSearchAllResults(searchAllQuery,searchPageNumber)
//            2->getSearchMovieResults( searchAllQuery,searchPageNumber )
//            3->getSearchTvResults(searchAllQuery,searchPageNumber)
//        }
//
//    }

//    fun getSearchMovieResults( query:String,pageNumber: Int ) {
//
//        searchAllQuery = query
//        searchPageNumber = pageNumber
//
//        viewModelScope.launch {
//
//            repository.getSearchMovieResult(query,pageNumber)
//                .let { response ->
//
//                    if(response.isSuccessful){
//                        _searchMovieResults.postValue(response.body()?.trendingItems)
//                        Log.d("TMDBResponse","SearchResponse: ${response.body()?.trendingItems.toString()}")
//                    }else{
//                        Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
//                    }
//
//                }
//
//        }
//
//    }


//    fun getSearchTvResults( query:String,pageNumber: Int ) {
//
//        viewModelScope.launch {
//
//            repository.getSearchTvResult(query,pageNumber)
//                .let { response ->
//
//                    if(response.isSuccessful){
//                        _searchTvResults.postValue(response.body()?.trendingItems)
//                        Log.d("TMDBResponse","SearchResponse: ${response.body()?.trendingItems.toString()}")
//                    }else{
//                        Log.d("TMDBResponse","Error: ${response.errorBody().toString()}")
//                    }
//
//                }
//
//        }
//
//    }

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




    fun getSearchAllResults(query: String){
        testPagingList = Pager(PagingConfig(pageSize = 20)){
            SearchPagingDataSource(tmdbService,query,Constants.ALL)
        }.flow.cachedIn(viewModelScope)
    }

    fun getSearchMovieResults(query: String){
        testPagingList = Pager(PagingConfig(pageSize = 20)){
            SearchPagingDataSource(tmdbService,query,Constants.MOVIES)
        }.flow.cachedIn(viewModelScope)
    }

    fun getSearchTvResults(query: String){
        testPagingList = Pager(PagingConfig(pageSize = 20)){
            SearchPagingDataSource(tmdbService,query,Constants.TV)
        }.flow.cachedIn(viewModelScope)
    }

}