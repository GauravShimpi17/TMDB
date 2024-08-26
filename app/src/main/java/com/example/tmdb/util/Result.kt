package com.example.tmdb.util

sealed class Result<T> (
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Result<T>(data = data)
    class Failure<T>(message: String?, data: T?) : Result<T>(message= message, data = data)

    companion object{
        fun<T> Success(data : T) : Result<T> {
            return Success(data)
        }
        fun <T> Failure(message: String? = null, data : T? = null) : Result<T>{
            return Failure(message,data)
        }
    }
}