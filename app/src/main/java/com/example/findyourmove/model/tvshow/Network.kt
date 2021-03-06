package com.example.findyourmove.model.tvshow


import com.google.gson.annotations.SerializedName

data class Network(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("origin_country")
    val originCountry: String
)