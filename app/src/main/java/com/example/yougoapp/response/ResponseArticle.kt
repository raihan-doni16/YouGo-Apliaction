package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

 class ResponseArticle(

	@field:SerializedName("artikel")
	val artikel: List<ArtikelItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

 class ArtikelItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("updateAt")
	val updateAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("webUrl")
	val webUrl: String ?= null,

	@field:SerializedName("title")
	val title: String? = null
)
