package com.rhezarijaya.githubone.utils

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorData: SingleEvent<String>) : Result<Nothing>()
    object Loading : Result<Nothing>()
}