package com.example.yougoapp.network

import com.example.yougoapp.response.ArticleResponse
import com.example.yougoapp.response.DetailResponse
import com.example.yougoapp.response.DetectionResponse
import com.example.yougoapp.response.LoginResponse
import com.example.yougoapp.response.PoseResponse
import com.example.yougoapp.response.ProfileResponse
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
import retrofit2.http.PUT
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

    @Multipart
    @POST("profile")
    suspend fun postProfile(
        @Part("id") id: String,
        @Part("email") email: String,
        @Part("firstName") firstName: String,
        @Part("lastName") lastName: String,
        @Part("age") age: String,
        @Part("weight") weight: String,
        @Part("height") height: String,
        @Part image: MultipartBody.Part
    ): ProfileResponse

    @Multipart
    @POST("profile")
    suspend fun  postPro(
        @Part("id") id: String,
        @Part("email") email: String,
        @Part("firstName") firstName: String,
        @Part("lastName") lastName: String,
        @Part("age") age: String,
        @Part("weight") weight: String,
        @Part("height") height: String,
        @Part image: MultipartBody.Part
    ): ProfileResponse
    @Multipart
    @PUT("profile")
    suspend fun putProfile(
        @Part("id") id: String,
        @Part("email") email: String,
        @Part("firstName") firstName: String,
        @Part("lastName") lastName: String,
        @Part("age") age: String,
        @Part("weight") weight: String,
        @Part("height") height: String,
        @Part image: MultipartBody.Part
    ): ProfileResponse

    @GET("profile")
    suspend fun getProfile(): ProfileResponse

    @PUT("profile")
    suspend fun updateProfile(): ProfileResponse

    @Multipart
    @POST("checkMyPose/{id}")
    suspend fun checkMyPose(
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): DetectionResponse


    @GET("articles")
    suspend fun geArt(): ResponseArticle


    @GET("poses")
    suspend fun getPoses(): ResponsePose
    @GET("poses/{id}")
    suspend fun getDetailPose(@Path("id")id:String): DetailResponse

}