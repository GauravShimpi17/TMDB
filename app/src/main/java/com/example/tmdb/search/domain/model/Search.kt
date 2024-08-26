package com.example.tmdb.search.domain.model


data class Search(
    val id: Long,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
)
