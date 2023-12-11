package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

 class LoginResponse(
    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("message")
    val message: String? = null,


    @field:SerializedName("error")
    val error: Boolean,
)

data class LoginResult(

    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("userId")
    val userId: String ? = null,


    @field:SerializedName("refreshToken")
    val refreshToken: String
)
