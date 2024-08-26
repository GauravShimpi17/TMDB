package com.example.tmdb.search.data.model


import com.google.gson.annotations.SerializedName

data class SearchResultDtoResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<SearchResultDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)