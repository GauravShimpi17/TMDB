package com.example.tmdb.presentation.util

data class PaginatorListState<T> (
    val page: Int,
    val list: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
)