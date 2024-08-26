package com.example.tmdb.search.domain.repository

import com.example.tmdb.search.data.remote.SearchApi
import com.example.tmdb.search.data.repository.SearchRepository
import com.example.tmdb.search.domain.model.Search
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun searchMovie(query: String, page: Int): Flow<Resource<List<Search>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val response = searchApi.searchMovie(query, page =  page)
                val searchResults = response.results.map { it.toSearch() }
                emit(Resource.Success(searchResults))
            } catch (e: IOException) {
                emit(Resource.Error("Error Loading Movies: ${e.localizedMessage}"))
            } catch (e: HttpException) {
                emit(Resource.Error("Error Loading Movies: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(Resource.Error("Error Loading Movies: ${e.localizedMessage}"))
            }
        }
    }
}
