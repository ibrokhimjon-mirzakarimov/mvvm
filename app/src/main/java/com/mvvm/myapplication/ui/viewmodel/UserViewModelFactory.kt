package com.mvvm.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm.myapplication.database.AppDatabase
import com.mvvm.myapplication.repository.ApiService
import com.mvvm.myapplication.utils.NetworkHelper
import java.lang.Exception

class UserViewModelFactory(private var appDatabase: AppDatabase,
                           private var apiService: ApiService,
                           private var networkHelper: NetworkHelper
                           ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(appDatabase,apiService,networkHelper) as T
        }
        return throw Exception("Error")
    }
}