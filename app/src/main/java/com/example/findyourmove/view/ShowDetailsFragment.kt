package com.example.findyourmove.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findyourmove.R
import com.example.findyourmove.databinding.FragmentShowDetailsBinding

class ShowDetailsFragment : Fragment() {

    private lateinit var binding: FragmentShowDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowDetailsBinding.inflate(layoutInflater)
        return binding.root
//        return inflater.inflate(R.layout.fragment_show_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvShowId.text = arguments?.getString("id")
    }
}