package com.example.yougoapp.data

data class UserData(
    val userId: String,
    val token: String,
    val refreshToken: String,
    val isLogin: Boolean = false
)