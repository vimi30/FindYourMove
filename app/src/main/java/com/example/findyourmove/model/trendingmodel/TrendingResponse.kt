package com.example.findyourmove.model.trendingmodel


import com.google.gson.annotations.SerializedName

data class TrendingResponse(
        @SerializedName("page")
    val page: Int,
        @SerializedName("results")
    val trendingItems: List<TrendingItem>,
        @SerializedName("total_pages")
    val totalPages: Int,
        @SerializedName("total_results")
    val totalResults: Int
)