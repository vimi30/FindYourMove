package com.example.findyourmove.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.findyourmove.databinding.ListviewItemBinding
import com.example.findyourmove.model.tvshow.Season

class SeasonListAdapter(private val  listOfSeason : List<Season>) : BaseAdapter() {
    override fun getCount(): Int {
        return listOfSeason.size
    }

    override fun getItem(position: Int): Any {
        return listOfSeason[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding = ListviewItemBinding.inflate(LayoutInflater.from(parent?.context),parent,false)

        val season:Season = listOfSeason[position]

        binding.apply {
            seasonNumber.text = season.name
            numberOfEpisodes.text = "Episodes: ${season.episodeCount}"
        }

        return binding.root
    }

    override fun isEnabled(position: Int): Boolean {
        return false
    }
}