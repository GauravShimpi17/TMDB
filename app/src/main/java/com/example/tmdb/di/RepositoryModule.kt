/*
package com.example.tmdb.di

import com.example.tmdb.home.data.remote.MovieApi
import com.example.tmdb.home.data.repository.MovieListRepoImpl
import com.example.tmdb.home.domain.repository.MovieListRepo
import com.example.tmdb.movieDetail.data.remote.MovieDetailApi
import com.example.tmdb.movieDetail.data.repository.MovieDetailRepoImpl
import com.example.tmdb.movieDetail.domain.repository.MovieDetailRepo
import com.example.tmdb.search.data.remote.SearchApi
import com.example.tmdb.search.data.repository.SearchRepository
import com.example.tmdb.search.domain.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepo(movieApi: MovieApi): MovieListRepo {
        return MovieListRepoImpl(movieApi)
    }

    @Provides
    @Singleton
    fun provideMovieDetailRepo(movieDetailApi: MovieDetailApi): MovieDetailRepo {
        return MovieDetailRepoImpl(movieDetailApi)
    }

    @Provides
    @Singleton
    fun provideSearchRepo(searchApi: SearchApi) : SearchRepository {
        return SearchRepositoryImpl(searchApi)
    }
}*/
