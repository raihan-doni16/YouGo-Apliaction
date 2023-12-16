package com.example.yougoapp.network

import com.example.yougoapp.response.ArticleResponse
import com.example.yougoapp.response.DetectionResponse
import com.example.yougoapp.response.LoginResponse
import com.example.yougoapp.response.PoseResponse
import com.example.yougoapp.response.RegisterResponse
import com.example.yougoapp.response.ResponseArticle
import com.example.yougoapp.response.ResponsePose
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {


    @FormUrlEncoded
    @POST("users")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("authentications")
     suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("profile")
    suspend fun pushProfile(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("age") age: String,
        @Field("weight") weight: String,
        @Field("height") height: String
    )
    @Multipart
    @POST("checkMyPose/{id}")
    suspend fun checkMyPose(
        @Path("id") id: String,
        @Part photo: MultipartBody.Part
    ): DetectionResponse


    @GET("articles")
    suspend fun geArt(): ResponseArticle


    @GET("poses")
    suspend fun getPoses(): ResponsePose
}