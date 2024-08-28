package com.example.tmdb.movieDetail.data.model

import com.example.tmdb.movieDetail.domain.model.MovieDetails
import com.google.gson.annotations.SerializedName
data class MovieDetailDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    val recommendations: Recommendations,
    val credits: CreditsDto,
){
    fun toDomain() : MovieDetails {
        return MovieDetails(
            adult,
            backdropPath,
            budget,
            genres,
            homepage,
            id,
            imdbId,
            originCountry,
            originalLanguage,
            originalTitle,
            overview,
            popularity,
            posterPath,
            releaseDate,
            revenue,
            runtime,
            status,
            tagline,
            title,
            video,
            voteAverage,
            voteCount,
            recommendations = recommendations.results.map { it.toMovie() },
            credits = credits.cast.map { it.toCast() }
        )
    }
}
