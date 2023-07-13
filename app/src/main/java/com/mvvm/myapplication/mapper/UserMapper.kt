package com.mvvm.myapplication.mapper

import com.mvvm.myapplication.database.entity.UserEntity
import com.mvvm.myapplication.models.UserBean

fun UserBean.mapToEntity(userBean: UserBean): UserEntity{
    return UserEntity(userBean.id?:0,userBean.login?:"",userBean.avatar_url?:"")
}