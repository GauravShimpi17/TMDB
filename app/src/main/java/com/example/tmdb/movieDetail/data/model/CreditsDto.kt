package com.example.tmdb.movieDetail.data.model


import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("cast")
    val cast: List<CastDto>,
    @SerializedName("crew")
    val crew: List<CrewDto>,
    @SerializedName("id")
    val id: Int
)