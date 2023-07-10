package com.mvvm.myapplication.repository

import com.mvvm.myapplication.models.UserBean
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    fun getUsers(): Flow<List<UserBean>>
}