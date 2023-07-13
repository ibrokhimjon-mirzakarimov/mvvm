package com.mvvm.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mvvm.myapplication.database.AppDatabase
import com.mvvm.myapplication.databinding.ActivityMainBinding
import com.mvvm.myapplication.repository.ApiClient
import com.mvvm.myapplication.ui.Resource
import com.mvvm.myapplication.ui.viewmodel.UserViewModel
import com.mvvm.myapplication.ui.viewmodel.UserViewModelFactory
import com.mvvm.myapplication.utils.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this,
            UserViewModelFactory(
                AppDatabase.getInstance(this),
                ApiClient.apiService,
                NetworkHelper(this))
            )[UserViewModel::class.java]


        launch {
            userViewModel.getStateFlow()
                .collect{
                    when(it){
                        is Resource.Error -> {
                            Toast.makeText(this@MainActivity, it.e.message, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            Log.d(TAG, "onCreate: ${it.data}")
                        }
                    }
                }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}