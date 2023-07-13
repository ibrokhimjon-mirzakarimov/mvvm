package com.mvvm.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val id :Int,
    val login: String,
    val avatar: String
)