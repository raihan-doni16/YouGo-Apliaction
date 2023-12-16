package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

 class ResponsePose(

	@field:SerializedName("pose")
	val pose: List<PoseItem> ,

	@field:SerializedName("status")
	val status: String? = null
)

 class PoseItem(

	@field:SerializedName("imageUrl")
	val imageUrl: String ,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("category")
	val category: String
)
