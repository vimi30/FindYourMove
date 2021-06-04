package com.example.findyourmove.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourmove.databinding.FooterLayoutBinding

class SearchLoadStateAdapter: LoadStateAdapter<SearchLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(FooterLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    inner class LoadStateViewHolder(private val binding: FooterLayoutBinding)
        : RecyclerView.ViewHolder(binding.root){

            fun bind(loadState: LoadState){
                binding.progressBar.isVisible = loadState is LoadState.Loading
            }

    }
}