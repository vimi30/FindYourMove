package com.example.findyourmove.adapters

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.MovieItemBinding
import com.example.findyourmove.model.moviemodels.MovieResponseItem

class MovieAdapter(private val listener: OnMovieClickListener, private val listType : String) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    inner class MyPopularMovieHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        override fun onClick(v: View?) {
            listener.onPopularMovieClick(absoluteAdapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }


    }

    inner class MyTrendingMovieHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        override fun onClick(v: View?) {
            listener.onTrendingMovieClick(absoluteAdapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }


    }

    inner class MyUpcomingMovieHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        override fun onClick(v: View?) {
            listener.onUpcomingMovieClick(absoluteAdapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }


    }


    private val diffUtilCallBack  = object : DiffUtil.ItemCallback<MovieResponseItem>(){
        override fun areItemsTheSame(oldItem: MovieResponseItem, newItem: MovieResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResponseItem, newItem: MovieResponseItem): Boolean {
            return newItem == oldItem
        }


    }

    private val differ = AsyncListDiffer(this,diffUtilCallBack)
    var movieList : List<MovieResponseItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(listType){
            Constants.POPULAR ->return MyPopularMovieHolder(MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))

            Constants.TRENDING -> return MyTrendingMovieHolder(MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))

            Constants.UPCOMING -> return MyUpcomingMovieHolder(MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
        }

        return return MyPopularMovieHolder(MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMovie = movieList[position]

        when(listType){
            Constants.TRENDING->{
                holder as MyTrendingMovieHolder
                holder.binding.apply {
                    mImg.load(Constants.IMG_BASE_URL+currentMovie.posterPath){
                        crossfade(true)
                        crossfade(1000)
                    }
                    ratingBar.rating = ((currentMovie.voteAverage/2).toFloat())
                }

            }

            Constants.POPULAR->{
                holder as MyPopularMovieHolder
                holder.binding.apply {
                    mImg.load(Constants.IMG_BASE_URL+currentMovie.posterPath){
                        crossfade(true)
                        crossfade(1000)
                    }
                    ratingBar.rating = ((currentMovie.voteAverage/2).toFloat())
                }


            }

            Constants.UPCOMING->{
                holder as MyUpcomingMovieHolder
                holder.binding.apply {
                    mImg.load(Constants.IMG_BASE_URL+currentMovie.posterPath){
                        crossfade(true)
                        crossfade(1000)
                    }
                    ratingBar.rating = ((currentMovie.voteAverage/2).toFloat())
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    interface OnMovieClickListener{
        fun onPopularMovieClick(position: Int)
        fun onTrendingMovieClick(position: Int)
        fun onUpcomingMovieClick(position: Int)
    }

}