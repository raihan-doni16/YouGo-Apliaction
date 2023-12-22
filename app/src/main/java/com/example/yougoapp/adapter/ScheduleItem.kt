package com.example.yougoapp.adapter

import com.google.gson.annotations.SerializedName

data class scheduleItem(
    @field:SerializedName("scheduleName")
    val scheduleName : String = "",

    @field:SerializedName("dayTime")
    val dayTime: String  ,

    @field:SerializedName("poseId")
    val poseId : String
)
