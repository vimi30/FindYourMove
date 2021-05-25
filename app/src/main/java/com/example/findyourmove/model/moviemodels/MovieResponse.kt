package com.example.findyourmove.model.moviemodels


import com.google.gson.annotations.SerializedName

data class MovieResponse(
        @SerializedName("page")
    val page: Int,
        @SerializedName("results")
    val movieResponseItems: List<MovieResponseItem>,
        @SerializedName("total_pages")
    val totalPages: Int,
        @SerializedName("total_results")
    val totalResults: Int
)