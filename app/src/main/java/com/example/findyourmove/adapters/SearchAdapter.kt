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
import com.example.findyourmove.model.trendingmodel.TrendingItem

class SearchAdapter(private val searchResultClickListener: OnSearchResultItemClick,private val listType : String) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MySearchHolder(val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        override fun onClick(v: View?) {
            searchResultClickListener.onSearchItemClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    inner class MyTrendingMediaHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        override fun onClick(v: View?) {
            searchResultClickListener.onTrendingItemClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    private val differUtilCallBack = object : DiffUtil.ItemCallback<TrendingItem>(){
        override fun areItemsTheSame(oldItem: TrendingItem, newItem: TrendingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TrendingItem, newItem: TrendingItem): Boolean {
            return newItem == oldItem
        }

    }

    private val differ  = AsyncListDiffer(this,differUtilCallBack)
    var searchList : List<TrendingItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySearchHolder {
//        return MySearchHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
//    }
//
//    override fun onBindViewHolder(holder: MySearchHolder, position: Int) {
//        var currentItem = searchList[position]
//
//        holder.binding.apply {
//            ivPoster.load(Constants.IMG_BASE_URL+currentItem.backdropPath){
//                crossfade(true)
//                crossfade(1000)
//            }
//
//            if(currentItem.name.isNullOrEmpty()){
//                contentTitle.text = currentItem.title
//            }else{
//                contentTitle.text = currentItem.name
//            }
//
//            if(currentItem.releaseDate!=null && currentItem.releaseDate.length>4){
//                releaseYear.text = currentItem.releaseDate.subSequence(0,4)
//            }else{
//
//                if(currentItem.firstAirDate!=null && currentItem.firstAirDate.length>4 ){
//                    releaseYear.text = currentItem.firstAirDate.subSequence(0,4)
//                }
//
//            }
//
//            ratingBar.rating = (currentItem.voteAverage/2).toFloat()
//
//        }
//
//
//    }
//
//    override fun getItemCount(): Int {
//        return searchList.size
//    }
//

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(listType==Constants.SEARCH){
            return MySearchHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
         return return MyTrendingMediaHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var currentItem = searchList[position]

        if(listType==Constants.SEARCH){
            holder as MySearchHolder
            holder.binding.apply {
            ivPoster.load(Constants.IMG_BASE_URL+currentItem.backdropPath){
                crossfade(true)
                crossfade(1000)
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

            ratingBar.rating = (currentItem.voteAverage/2).toFloat()
            }

        }else{

            holder as MyTrendingMediaHolder
            holder.binding.apply {
            mImg.load(Constants.IMG_BASE_URL+currentItem.backdropPath){
                crossfade(true)
                crossfade(1000)
            }
            ratingBar.rating = (currentItem.voteAverage/2).toFloat()
            }

        }

    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    interface OnSearchResultItemClick{
        fun onSearchItemClick(position: Int);
        fun onTrendingItemClick(position: Int)
    }

}