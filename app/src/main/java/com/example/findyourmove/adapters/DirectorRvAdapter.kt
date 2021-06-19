package com.example.findyourmove.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.findyourmove.api.Constants
import com.example.findyourmove.databinding.GridItemBinding
import com.example.findyourmove.model.credit.Crew

class DirectorRvAdapter(private val listOfCrew: List<Crew>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class DirectorViewHolder(val binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return DirectorViewHolder(GridItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crewMember = listOfCrew[position]
        holder as DirectorViewHolder
        holder.binding.apply {
            actorImg.load(Constants.IMG_BASE_URL+crewMember.profilePath){
                crossfade(true)
                crossfade(1000)
            }
            actorName.text = crewMember.name
        }
    }

    override fun getItemCount(): Int {
        return listOfCrew.size
    }
}