package com.example.yougoapp.network

import com.example.yougoapp.response.ArticleResponse
import com.example.yougoapp.response.LoginResponse
import com.example.yougoapp.response.PoseResponse
import com.example.yougoapp.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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

    @GET("articles")
    fun getArticles(): ArticleResponse

    @GET("poses")
    suspend fun getPoses(): PoseResponse
}