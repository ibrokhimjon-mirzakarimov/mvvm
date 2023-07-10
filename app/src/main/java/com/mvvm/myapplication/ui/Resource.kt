package com.mvvm.myapplication.ui

sealed class Resource<T>{
    class Loading<T>:Resource<T>()
    class Success<T: Any>(val data: T) : Resource<T>()
    class Error<T: Any>(val e: Throwable): Resource<T>()

}