package com.example.tmdb.home.data.model

import com.google.gson.annotations.SerializedName
data class PopularDtoResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<PopularDto>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)
