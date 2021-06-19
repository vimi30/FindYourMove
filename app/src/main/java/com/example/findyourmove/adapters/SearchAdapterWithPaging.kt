package com.example.findyourmove.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.SearchItemBinding
import com.example.findyourmove.model.trendingmodel.TrendingItem

class SearchAdapterWithPaging( private val clickListener: OnSearchItemClickListener) : PagingDataAdapter<TrendingItem,RecyclerView.ViewHolder>(DiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder as SearchViewHolder
       if(currentItem!=null){
           holder.bind(currentItem)
       }
    }


    inner class SearchViewHolder(val binding: SearchItemBinding) :  RecyclerView.ViewHolder(binding.root){


        init {
            binding.root.setOnClickListener{
                val position = absoluteAdapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if(item!=null){
                        clickListener.onSearchItemClick(item)
                    }
                }
            }
        }

        fun bind(currentItem: TrendingItem){
            binding.apply {
                if (currentItem != null) {
                    if(!currentItem.backdropPath.isNullOrEmpty()){
                        ivPoster.load(Constants.IMG_BASE_URL+currentItem.backdropPath){
                            crossfade(true)
                            crossfade(1000)
                        }
                    }

                    if(currentItem.name.isNullOrEmpty()){
                        contentTitle.text = currentItem.title
                    }else{
                        contentTitle.text = currentItem.name
                    }

                    if(currentItem.releaseDate!=null && currentItem.releaseDate.length>4){
                        releaseYear.text = currentItem.releaseDate.subSequence(0,4)
                    }else{
                        if(currentItem.firstAirDate!=null && currentItem.firstAirDate.length>4 ){
                            releaseYear.text = currentItem.firstAirDate.subSequence(0,4)
                        }
                    }
                    ratingBar.rating = (currentItem.voteAverage/2).toFloat()}
                }
        }


    }


    object DiffUtilCallBack : DiffUtil.ItemCallback<TrendingItem>(){
        override fun areItemsTheSame(oldItem: TrendingItem, newItem: TrendingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TrendingItem, newItem: TrendingItem): Boolean {
            return oldItem == newItem
        }

    }

    interface OnSearchItemClickListener{

        fun onSearchItemClick(searchItem : TrendingItem)

    }



}