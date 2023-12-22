package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

 data class ProfileResponse(

	@field:SerializedName("userProfile")
	val userProfile: UserProfile,

	@field:SerializedName("status")
	val status: String? = null
)

data class UserProfile(

	@field:SerializedName("profile")
	val profile: Profile,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

 data class Profile(

	@field:SerializedName("firstName")
	val firstName: String,

	@field:SerializedName("lastName")
	val lastName: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("idealWeightRange")
	val idealWeightRange: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("bmi")
	val bmi: String,

	@field:SerializedName("status")
	val status: String
)


