package com.example.tmdb.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.model.Movie
import com.example.tmdb.home.domain.repository.MovieListRepo
import com.example.tmdb.core.presentation.util.PaginatorListState
import com.example.tmdb.util.Categories
import com.example.tmdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieListRepo: MovieListRepo
) : ViewModel() {

//    private val _movieListPopular =
//        MutableStateFlow<Resource<List<PopularDto>>>(Resource.Loading(false))
//    val movieListPopular: StateFlow<Resource<List<PopularDto>>> = _movieListPopular.asStateFlow()
//
//    private val _movieListUpcoming =
//        MutableStateFlow<Resource<List<PopularDto>>>(Resource.Loading(false))
//    val movieListUpcoming: StateFlow<Resource<List<PopularDto>>> = _movieListUpcoming.asStateFlow()

    private val _movieListPopular =
        MutableStateFlow<PaginatorListState<Movie>>(PaginatorListState(1))
    val movieListPopular: StateFlow<PaginatorListState<Movie>> = _movieListPopular.asStateFlow()

    private val _movieListUpcoming =
        MutableStateFlow<PaginatorListState<Movie>>(PaginatorListState(1))
    val movieListUpcoming: StateFlow<PaginatorListState<Movie>> = _movieListUpcoming.asStateFlow()
    fun fetchMovies(category: String, page: Int) {
        viewModelScope.launch {
            movieListRepo.getMovieList(
                forceFetchFromRemote = true,
                category = category,
                page = page
            ).collect { resource ->
                if (category == Categories.POPULAR.value) {
                    when(resource){
                        is Resource.Error -> {
                            _movieListPopular.value = _movieListPopular.value.copy(
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _movieListPopular.value = _movieListPopular.value.copy(isLoading = true)

                        }
                        is Resource.Success -> {
                            val res = _movieListPopular.value
                            _movieListPopular.value = res.copy(
                                page = res.page+1,
                                list = res.list + resource.data!!,
                                isLoading = false
                            )
                        }
                    }
                } else {
                    when(resource){
                        is Resource.Error -> {
                            _movieListUpcoming.value = _movieListUpcoming.value.copy(
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _movieListUpcoming.value = _movieListUpcoming.value.copy(isLoading = true)

                        }
                        is Resource.Success -> {
                            val res = _movieListUpcoming.value
                            _movieListUpcoming.value = res.copy(
                                page = res.page+1,
                                list = res.list + resource.data!!,
                                isLoading = false
                            )
                        }
                    }
                }

            }
        }
    }


}