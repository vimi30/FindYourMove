package com.example.findyourmove.model.WatchProvider


import com.google.gson.annotations.SerializedName

data class Flatrate(
    @SerializedName("display_priority")
    val displayPriority: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("provider_id")
    val providerId: Int,
    @SerializedName("provider_name")
    val providerName: String
)