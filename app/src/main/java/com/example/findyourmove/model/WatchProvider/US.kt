package com.example.findyourmove.model.WatchProvider


import com.google.gson.annotations.SerializedName

data class US(
    @SerializedName("link")
    val link: String,
    @SerializedName("flatrate")
    val flatrate: List<Flatrate>
)