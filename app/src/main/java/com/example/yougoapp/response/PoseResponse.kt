package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName

data class PoseResponse(

    @field:SerializedName("poseItem")
    val data: List<PoseItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,
)

data class PoseItem(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("step")
    val step: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("time")
    val time: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("category")
    val category: String? = null
)
