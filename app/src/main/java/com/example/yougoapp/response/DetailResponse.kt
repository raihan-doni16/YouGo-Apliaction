package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

 data class DetailResponse(

	@field:SerializedName("pose")
	val pose: DetailPose,

	@field:SerializedName("status")
	val status: String? = null
)

 data class DetailPose(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("detail")
	val detail: List<DetailItem>,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)

 data class DetailItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("stepId")
	val stepId: String,

	@field:SerializedName("step")
	val step: String,

	@field:SerializedName("time")
	val time: Long
)
