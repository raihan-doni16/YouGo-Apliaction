package com.example.yougoapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.yougoapp.data.State
import com.example.yougoapp.data.UserData
import com.example.yougoapp.data.YogaPreference
import com.example.yougoapp.network.ApiConfig
import com.example.yougoapp.network.ApiService
import com.example.yougoapp.response.ArticleItem
import com.example.yougoapp.response.ArtikelItem
import com.example.yougoapp.response.Data
import com.example.yougoapp.response.DetailResponse
import com.example.yougoapp.response.DetectionResponse
import com.example.yougoapp.response.ErrorResponse
import com.example.yougoapp.response.LoginResponse
import com.example.yougoapp.response.PoseItem

import com.example.yougoapp.response.PoseResponse
import com.example.yougoapp.response.Profile
import com.example.yougoapp.response.ProfileResponse
import com.example.yougoapp.response.RegisterResponse
import com.example.yougoapp.response.UserProfile
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.net.SocketTimeoutException

class YogaRepository private constructor(
    private val yogaPreference: YogaPreference,
    private val apiService: ApiService
) {
    companion object {
        @Volatile
        private var instance: YogaRepository? = null
        fun getInstance(
            yogaPreference: YogaPreference, apiService: ApiService
        ): YogaRepository =
            instance ?: synchronized(this) {
                instance ?: YogaRepository(yogaPreference, apiService)
            }.also { instance = it }
    }

    fun register(email: String, password: String): LiveData<State<RegisterResponse>> = liveData {
        emit(State.Loading)
        try {
            val response = apiService.register(email, password)
            emit(State.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            emit(State.Error("Registration failed: $errorMessage"))

        } catch (e: Exception) {
            emit(State.Error("Internet Issues"))
        }
    }

    fun login(email: String, password: String): LiveData<State<LoginResponse>> = liveData {
        emit(State.Loading)
        try {
            val response = apiService.login(email, password)

            if (!response.error) {
                val loginResult = response.loginResult

                if (loginResult != null) {
                    val input = UserData(
                        userId = loginResult.userId ?: "",
                        token = loginResult.accessToken,
                        refreshToken = loginResult.refreshToken,
                        isLogin = true
                    )
                    Log.d("cek", loginResult.toString())
                    ApiConfig.token = loginResult.accessToken
                    yogaPreference.saveSession(input)
                    emit(State.Success(response))
                } else {
                    emit(State.Error("Login result is null"))
                }
            } else {
                emit(State.Error(response.message ?: "Error"))
            }
        } catch (e: HttpException) {
            emit(State.Error("Login failed: ${e.message()}"))
        } catch (e: IOException) {
            emit(State.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(State.Error("An unexpected error occurred: ${e.message}"))
        }
    }

    fun detection(id: String, image: File): LiveData<State<DetectionResponse>> = liveData {
        emit(State.Loading)

        val requestPhotoFile = image.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("image", image.name, requestPhotoFile)



        try {
            val pref = yogaPreference.getSession().first()
            val response = ApiConfig.getApiService(pref.token)
            val success = response.checkMyPose(id, multipartBody)
            Log.d("cek", success.status)
            emit(State.Success(success))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, DetectionResponse::class.java)
            emit(State.Error("Error: ${errorResponse.status}"))

            Log.e("detection", "HttpException occurred", e)
        } catch (e: Exception) {

            Log.e("detection", "Request Payload: $requestPhotoFile")
            val errorBody = e.localizedMessage

            Log.e("detection", "Response Body: $errorBody")
            emit(State.Error("Unknown error occurred"))
            Log.e("detection", "Exception occurred", e)
        }
    }
    fun postProfile( email: String,firstName: String, lastName: String, age: String, weight: String,  height: String,image: File): LiveData<State<ProfileResponse>> = liveData {
        emit(State.Loading)
        try {
            val requestFile = image.asRequestBody("image/jpeg".toMediaType())
            val photoPart = MultipartBody.Part.createFormData("imageUrl", image.name, requestFile)
            val pref = yogaPreference.getSession().first()
            val response = ApiConfig.getApiService(pref.token)
            Log.d("cek", pref.userId)
            val post = response.postPro(pref.userId,email,firstName,lastName,age,weight,height,photoPart)
            emit(State.Success(post))
        }catch (e: HttpException){
            emit(State.Error("Login failed: ${e.message()}"))
        } catch (e: IOException) {
            emit(State.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(State.Error("An unexpected error occurred: ${e.message}"))
        }
    }
    fun getDetailPose(id: String): LiveData<State<DetailResponse>> = liveData {
        emit(State.Loading)
        try {
            val pref = yogaPreference.getSession().first()
            val response = ApiConfig.getApiService(pref.token)
            val detail = response.getDetailPose(id)
            emit(State.Success(detail))
        }catch (e: HttpException){
            emit(State.Error("Login failed: ${e.message()}"))
        } catch (e: IOException) {
            emit(State.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(State.Error("An unexpected error occurred: ${e.message}"))
        }
    }
    fun updateProfile(email: String,firstName: String, lastName: String, age: String, weight: String, image: File, height: String): LiveData<State<ProfileResponse>> = liveData {
        emit(State.Loading)
        try {
            val requestPhotoFile = image.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData("photo", image.name, requestPhotoFile)
            val pref = yogaPreference.getSession().first()
            val response = ApiConfig.getApiService(pref.token)
            val post = response.postProfile(pref.userId,email,firstName,lastName,age,weight,height,multipartBody)
            emit(State.Success(post))
        }catch (e: HttpException){
            emit(State.Error("Login failed: ${e.message()}"))
        } catch (e: IOException) {
            emit(State.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(State.Error("An unexpected error occurred: ${e.message}"))
        }
    }

    fun getArticle(): LiveData<State<List<ArtikelItem>>> = liveData {
        emit(State.Loading)

        try {
            val pref = runBlocking {
                yogaPreference.getSession().first()
            }
            val response = ApiConfig.getApiService(pref.token)

            val articleResponse = response.geArt()
            Log.d("before map", "Before map operation")
            val article = articleResponse.artikel
            val nonNullableArticleList = article?.map { data ->
                ArtikelItem(
                    data?.createdAt,
                    data?.imageUrl,
                    data?.description,
                    data?.updateAt,
                    data?.id,
                    data?.webUrl,
                    data?.title

                )
            } ?: emptyList()

            emit(State.Success(nonNullableArticleList))
        }catch (e:HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            emit(State.Error("Failed: $errorMessage"))
        }catch (e: Exception){
            emit(State.Error("Internet Issues"))
        }catch (e: SocketTimeoutException){
            emit(State.Error("Read timeout occurred"))
        }

    }
    fun getProfile(): LiveData<State<UserProfile>> = liveData {
        emit(State.Loading)
        try {
            val preference = runBlocking {
                yogaPreference.getSession().first()
            }
            val response = ApiConfig.getApiService(preference.token)
            val profileResponse = response.getProfile()
            val profile = profileResponse.userProfile
            emit(State.Success(profile))
        }catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            emit(State.Error("Failed: $errorMessage"))
        }catch (e: Exception){
            emit(State.Error("Internet Issues"))
        }catch (e: SocketTimeoutException){
            emit(State.Error("Read timeout occurred"))
        }

    }

    fun getPose(): LiveData<State<List<PoseItem>>> = liveData {
        emit(State.Loading)
        try {
            val pref = runBlocking {
                yogaPreference.getSession().first()
            }
            val response = ApiConfig.getApiService(pref.token)
            val poseResponse = response.getPoses()
            val pose = poseResponse.pose
            val poseList = pose.map { data ->
                PoseItem(
                    data.imageUrl,
                    data.id,
                    data.title,
                    data.category
                )
            }
            if(poseList.isEmpty()){
                Log.d("Error", poseList.toString())
            }else{
                Log.d("success", poseList.toString())
            }


            emit(State.Success(poseList))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            emit(State.Error("Failed: $errorMessage"))
        }catch (e: Exception){
            emit(State.Error("Internet Issues"))
        }catch (e: SocketTimeoutException){
            emit(State.Error("Read timeout occurred"))
        }
    }
    suspend fun saveSession(user: UserData){
        yogaPreference.saveSession(user)
    }
    fun getSession(): Flow<UserData>{
        return  yogaPreference.getSession()
    }
    suspend fun logout(){
        yogaPreference.logout()
    }
}
