package com.example.tmdb.movieDetail.data.remote

import com.example.tmdb.BuildConfig
import com.example.tmdb.movieDetail.data.model.MovieDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailApi {

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Long,
        @Query("append_to_response") appendToResponse: String = "credits,recommendations,videos",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDetailDto

//    companion object {
//        const val BASE_URL = "https://api.themoviedb.org/3/"
//        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
//    }
}
