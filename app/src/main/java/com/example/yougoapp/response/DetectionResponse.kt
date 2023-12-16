package com.example.yougoapp.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

class DetectionResponse(
    @field:SerializedName("pose")
    val pose: Pose,
    @field:SerializedName("status")
    val status: String
)

class Pose(
    @field:SerializedName("yoga_pose")
    val yogaPose: String,
    @field:SerializedName("isCorrectPose")
    val confidence: Any,
    @field:SerializedName("confidence")
    val isCorrectPose: Boolean
)

