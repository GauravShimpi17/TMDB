package com.example.tmdb.presentation.movieDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.model.Movie
import com.example.tmdb.domain.model.MovieDetails
import com.example.tmdb.domain.repository.MovieListRepo
import com.example.tmdb.presentation.util.PaginatorListState
import com.example.tmdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieListRepo: MovieListRepo
) : ViewModel() {

    private val _getMovieDetail = MutableStateFlow<Resource<MovieDetails>>(Resource.Loading(false))
    val getMovieDetail: StateFlow<Resource<MovieDetails>> = _getMovieDetail.asStateFlow()

    private val _getMovieRecommendations = MutableStateFlow<PaginatorListState<Movie>>(PaginatorListState(page = 1))
    val getMovieRecommendations: StateFlow<PaginatorListState<Movie>> = _getMovieRecommendations

    fun fetchDetails(id : Long){
        viewModelScope.launch {
            movieListRepo.getMovieById(id)
                .collect{details ->
                    _getMovieDetail.value = details
                }
        }
    }

    fun fetchRecommendations(id: Long, page : Int) {
        viewModelScope.launch {
            movieListRepo.getMovieRecommendation(id, page)
                .collect{recommendations->
                    when(recommendations){
                        is Resource.Error -> {
                            _getMovieRecommendations.value = _getMovieRecommendations.value.copy(
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _getMovieRecommendations.value = _getMovieRecommendations.value.copy(isLoading = true)

                        }
                        is Resource.Success -> {
                            val res = _getMovieRecommendations.value
                            _getMovieRecommendations.value = res.copy(
                                page = res.page+1,
                                list = res.list + recommendations.data!!,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}