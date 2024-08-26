package com.example.tmdb.search.data.repository

import com.example.tmdb.search.domain.model.Search
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchMovie(
        query: String,
        page: Int
    ): Flow<Resource<List<Search>>>
}