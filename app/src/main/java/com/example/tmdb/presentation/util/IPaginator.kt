package com.example.tmdb.presentation.util

interface IPaginator<T> {
    suspend fun loadNext()
    suspend fun reset()
}