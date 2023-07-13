package com.mvvm.myapplication.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvm.myapplication.database.AppDatabase
import com.mvvm.myapplication.database.entity.UserEntity
import com.mvvm.myapplication.mapper.mapToEntity
import com.mvvm.myapplication.repository.ApiService
import com.mvvm.myapplication.repository.UserReporsitory
import com.mvvm.myapplication.ui.Resource
import com.mvvm.myapplication.utils.NetworkHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch

class UserViewModel(
     appDatabase: AppDatabase,
     apiService: ApiService,
    private val networkHelper: NetworkHelper
): ViewModel() {
    private val userReporsitory = UserReporsitory(apiService,appDatabase.userDao())
    private val stateFlow = MutableStateFlow<Resource<List<UserEntity>>>(Resource.Loading())

    init {
        fetchUsers()
    }

   @SuppressLint("SuspiciousIndentation")
   private fun fetchUsers(){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                userReporsitory.getUsers()
                    .catch { stateFlow.emit(Resource.Error(it)) }
                    .flatMapConcat { it ->
                        val list = ArrayList<UserEntity>()
                            it.forEach {
                                list.add(it.mapToEntity(it))
                            }
                        userReporsitory.addUsers(list)
                    }
                    .collect{
                        stateFlow.emit(Resource.Success(userReporsitory.getDbUsers()))
                    }
            }else{
                if (userReporsitory.getUsersCount() > 0){
                    stateFlow.emit(Resource.Success(userReporsitory.getDbUsers()))
                }else stateFlow.emit(Resource.Error(Throwable("Internet not connection")))
            }


        }
    }

    fun getStateFlow(): StateFlow<Resource<List<UserEntity>>>{
        return stateFlow;
    }
}