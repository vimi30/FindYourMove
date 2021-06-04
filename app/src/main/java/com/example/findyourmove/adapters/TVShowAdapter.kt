package com.example.findyourmove.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.MovieItemBinding
import com.example.findyourmove.databinding.SearchItemBinding
import com.example.findyourmove.model.tvshowmodel.TVShowResponseItem
import com.example.findyourmove.view.TvFragment

class TVShowAdapter(private val listener: OnShowClickListener,private val listType: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyTrendingShowHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        override fun onClick(v: View?) {
                listener.onTrendingShowClick(absoluteAdapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }

    }
    inner class MyPopularShowHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        override fun onClick(v: View?) {
            listener.onPopularShowClick(absoluteAdapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }

    }

    inner class MyTopShowHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        override fun onClick(v: View?) {
            listener.onTopRatedShowClick(absoluteAdapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }

    }


    private val diffUtilCallBack = object : DiffUtil.ItemCallback<TVShowResponseItem>(){
        override fun areItemsTheSame(oldItem: TVShowResponseItem, newItem: TVShowResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TVShowResponseItem, newItem: TVShowResponseItem): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this,diffUtilCallBack)

    var tvShows : List<TVShowResponseItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(listType){
            Constants.POPULAR-> return MyPopularShowHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            Constants.TRENDING-> return MyTrendingShowHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            Constants.TOP_RATED-> return MyTopShowHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

        }
       return MyPopularShowHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentShow = tvShows[position]

        when(listType){

            Constants.POPULAR -> {
                holder as MyPopularShowHolder
                holder.binding.apply {

                    mImg.load(Constants.IMG_BASE_URL+currentShow.posterPath){
                        crossfade(true)
                        crossfade(1000)
                    }

                    ratingBar.rating = (currentShow.voteAverage/2).toFloat()

                }

            }

            Constants.TRENDING -> {
                holder as MyTrendingShowHolder
                holder.binding.apply {

                    mImg.load(Constants.IMG_BASE_URL+currentShow.posterPath){
                        crossfade(true)
                        crossfade(1000)
                    }

                    ratingBar.rating = (currentShow.voteAverage/2).toFloat()

                }
            }
            Constants.TOP_RATED -> {
                holder as MyTopShowHolder
                holder.binding.apply {

                    mImg.load(Constants.IMG_BASE_URL+currentShow.posterPath){
                        crossfade(true)
                        crossfade(1000)
                    }

                    ratingBar.rating = (currentShow.voteAverage/2).toFloat()

                }

            }

        }


    }

    override fun getItemCount(): Int {
        return tvShows.size
    }


    interface OnShowClickListener{

        fun onTrendingShowClick(position: Int)
        fun onTopRatedShowClick(position: Int)
        fun onPopularShowClick(position: Int)

    }


}