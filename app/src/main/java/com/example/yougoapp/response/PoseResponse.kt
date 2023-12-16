package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

data class PoseResponse(

    @field:SerializedName("pose")
    val data: List<Data> = emptyList(),

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,
)

data class Data(

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("step")
    val step: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("time")
    val time: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("category")
    val category: String
)
