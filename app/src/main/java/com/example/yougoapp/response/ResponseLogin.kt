package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

 class ResponseLogin(

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
)
