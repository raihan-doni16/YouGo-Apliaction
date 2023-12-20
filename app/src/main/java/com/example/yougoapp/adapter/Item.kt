package com.example.yougoapp.adapter

import com.google.gson.annotations.SerializedName

data class Item(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("stepId")
    val stepId: String,

    @field:SerializedName("step")
    val step: String,

    @field:SerializedName("time")
    val time: Long
)