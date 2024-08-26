package com.example.tmdb.domain.repository

import com.example.tmdb.data.model.PopularDto
import com.example.tmdb.domain.model.Movie
import com.example.tmdb.domain.model.MovieDetails
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepo {
    suspend fun getMovieList(
        forceFetchFromRemote : Boolean,
        page : Int,
        category: String,
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovieById(id : Long) : Flow<Resource<MovieDetails>>
    suspend fun getMovieRecommendation(id: Long, page: Int) : Flow<Resource<List<Movie>>>
}