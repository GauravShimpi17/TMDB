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
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDetailApi(retrofit: Retrofit): MovieDetailApi {
        return retrofit.create(MovieDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }



}