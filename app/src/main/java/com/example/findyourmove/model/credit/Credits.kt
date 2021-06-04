package com.example.findyourmove.model.credit


import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>
)