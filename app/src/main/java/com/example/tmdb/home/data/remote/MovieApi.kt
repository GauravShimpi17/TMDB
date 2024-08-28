package com.example.tmdb.home.data.remote

import com.example.tmdb.BuildConfig
import com.example.tmdb.home.data.model.PopularDtoResponse
import com.example.tmdb.movieDetail.data.model.MovieDetailDto
import com.example.tmdb.movieDetail.data.model.Recommendations
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): PopularDtoResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Recommendations

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Long,
        @Query("append_to_response") appendToResponse: String = "credits,recommendations,videos",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDetailDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
//        const val APIKEY = "30c494b030b7b5d997e34180c3b514fd"
    }
}
