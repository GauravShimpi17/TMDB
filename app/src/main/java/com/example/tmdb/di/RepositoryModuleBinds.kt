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
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinds {

    @Binds @Singleton
    abstract fun provideMovieRepo(movieListRepoImpl: MovieListRepoImpl): MovieListRepo

    @Binds
    @Singleton
    abstract fun provideMovieDetailRepo(movieDetailRepoImpl: MovieDetailRepoImpl): MovieDetailRepo

    @Binds
    @Singleton
    abstract fun provideSearchRepo(searchRepositoryImpl : SearchRepositoryImpl) : SearchRepository
}