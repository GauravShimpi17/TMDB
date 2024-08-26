package com.example.tmdb.data.repository

import com.example.tmdb.data.model.MovieDetailsDto
import com.example.tmdb.data.remote.MovieApi
import com.example.tmdb.data.model.PopularDto
import com.example.tmdb.domain.model.Movie
import com.example.tmdb.domain.model.MovieDetails
import com.example.tmdb.domain.repository.MovieListRepo
import com.example.tmdb.util.Resource
import com.example.tmdb.util.Resource.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListRepoImpl @Inject constructor(
    private val movieApi: MovieApi,
) : MovieListRepo {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        page: Int,
        category: String
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Loading(true))

            try {
                val response = movieApi.getMovieList(category, page)
                emit(Success(response.results.map { it.toMovie() }))
            } catch (e: IOException) {
                emit(Error(message = "Error Loading Movies: ${e.localizedMessage}"))
            } catch (e: HttpException) {
                emit(Error(message = "Error Loading Movies: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(Error(message = "Error Loading Movies: ${e.localizedMessage}"))
            }
        }
    }

    override suspend fun getMovieById(id: Long): Flow<Resource<MovieDetails>> {
        return flow {
            emit(Loading(true))
            try {
                val response = movieApi.getMovieDetails(id).toDomain()
                emit(Success(response))
            } catch (e: IOException) {
                emit(Error(message = "Error Loading Movie: ${e.localizedMessage}"))
            } catch (e: HttpException) {
                emit(Error(message = "Error Loading Movie: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(Error(message = "Error Loading Movie: ${e.localizedMessage}"))
            }
        }
    }

    override suspend fun getMovieRecommendation(id: Long, page: Int): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Loading(true))
            try {
                val response = movieApi.getMovieDetails(id).toDomain()
                emit(Success(response.recommendations))
            }catch (e: IOException) {
                emit(Error(message = "Error Loading Movie: ${e.localizedMessage}"))
            } catch (e: HttpException) {
                emit(Error(message = "Error Loading Movie: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(Error(message = "Error Loading Movie: ${e.localizedMessage}"))
            }
        }
    }


}
