package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

class RegisterResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
