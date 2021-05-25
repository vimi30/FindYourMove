package com.example.findyourmove.model.WatchProvider


import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("US")
    val uS: US
)