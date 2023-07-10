package com.mvvm.myapplication.repository

class UserReporsitory(private val apiService: ApiService) {

    fun getUsers() = apiService.getUsers()
}