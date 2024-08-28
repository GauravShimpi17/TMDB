package com.example.tmdb.search.data.remote

import com.example.tmdb.BuildConfig
import com.example.tmdb.search.data.model.SearchResultDtoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): SearchResultDtoResponse

}