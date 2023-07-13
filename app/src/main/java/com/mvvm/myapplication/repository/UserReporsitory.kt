package com.mvvm.myapplication.repository

import com.mvvm.myapplication.database.dao.UserDao
import com.mvvm.myapplication.database.entity.UserEntity
import kotlinx.coroutines.flow.flow

class UserReporsitory(private val apiService: ApiService, private val userDao: UserDao) {

    fun getUsers() = apiService.getUsers()

    fun addUsers(list: List<UserEntity>) = flow{ emit(userDao.addUsers(list))}

    fun getDbUsers() = userDao.getUsers()

    fun getUsersCount() = userDao.getUsersCount()
}