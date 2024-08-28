package com.example.tmdb.movieDetail.domain.repository

import com.example.tmdb.core.data.model.Movie
import com.example.tmdb.movieDetail.domain.model.MovieDetails
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepo {
    /*suspend fun getMovieList(
        forceFetchFromRemote : Boolean,
        page : Int,
        category: String,
    ): Flow<Resource<List<Movie>>>
*/
    suspend fun getMovieById(id : Long) : Flow<Resource<MovieDetails>>
    suspend fun getMovieRecommendation(id: Long, page: Int) : Flow<Resource<List<Movie>>>
}