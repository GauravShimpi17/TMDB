package com.example.tmdb.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.search.data.repository.SearchRepository
import com.example.tmdb.search.domain.model.Search
import com.example.tmdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel(){
    private val _searchResults = MutableStateFlow<Resource<List<Search>>>(Resource.Loading())
    val searchResults: StateFlow<Resource<List<Search>>> = _searchResults.asStateFlow()

    fun searchMovie(query: String, page: Int) {
        viewModelScope.launch {
            searchRepository.searchMovie(query, page).collect { result ->
                _searchResults.value = result
            }
        }
    }
}