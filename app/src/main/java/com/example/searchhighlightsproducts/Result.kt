package com.example.searchhighlightsproducts

sealed class Result<T> {

    data class Success<T>(val result: T) : Result<T>()

    data class Error<T>(val error: Throwable) : Result<T>()

}