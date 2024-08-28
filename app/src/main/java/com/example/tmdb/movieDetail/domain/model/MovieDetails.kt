package com.example.tmdb.movieDetail.domain.model

import com.example.tmdb.core.data.model.Movie
import com.example.tmdb.movieDetail.data.model.Genre


data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String?,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    val imdbId: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long,
    val recommendations: List<Movie>,
    val credits: List<Cast>,
    )