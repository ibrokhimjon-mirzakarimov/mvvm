package com.mvvm.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvm.myapplication.models.UserBean
import com.mvvm.myapplication.repository.ApiClient
import com.mvvm.myapplication.repository.UserReporsitory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userReporsitory = UserReporsitory(ApiClient.apiService)
    private val stateFlow = MutableStateFlow<Resource<List<UserBean>>>(Resource.Loading())

    init {
        fetchUsers()
    }

    private fun fetchUsers(){
        viewModelScope.launch {
            userReporsitory.getUsers()
                .catch { stateFlow.emit(Resource.Error(it)) }
                .collect{
                    stateFlow.emit(Resource.Success(it))
                }
        }
    }

    fun getStateFlow(): StateFlow<Resource<List<UserBean>>>{
        return stateFlow;
    }
}