package com.example.findyourmove.model.video


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)