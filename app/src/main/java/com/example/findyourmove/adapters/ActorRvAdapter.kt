package com.example.findyourmove.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.GridItemBinding
import com.example.findyourmove.model.credit.Cast

class ActorRvAdapter(private val listOfActors: List<Cast>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ActorViewHolder(val binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ActorViewHolder(GridItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val actor = listOfActors[position]
        holder as ActorViewHolder
        holder.binding.apply {
            actorImg.load(Constants.IMG_BASE_URL+actor.profilePath){
                crossfade(true)
                crossfade(1000)
            }
            actorName.text = actor.name
            actorRole.text = actor.character
            actorRole.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return listOfActors.size
    }
}