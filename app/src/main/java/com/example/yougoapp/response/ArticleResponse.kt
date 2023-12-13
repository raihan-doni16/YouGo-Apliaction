package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @field:SerializedName("artikel")
    val data: List<ArticleItem> = emptyList(),

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    )

data class ArticleItem(

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

    @field:SerializedName("title")
    val title: String? = null
)
