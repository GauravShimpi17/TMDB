package com.example.tmdb.core.presentation.util

interface IPaginator<T> {
    suspend fun loadNext()
    suspend fun reset()
}