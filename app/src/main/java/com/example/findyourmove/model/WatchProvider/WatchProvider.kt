package com.example.findyourmove.model.WatchProvider


import com.google.gson.annotations.SerializedName

data class WatchProvider(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: Results
)