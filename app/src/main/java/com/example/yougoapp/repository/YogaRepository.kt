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
import com.example.yougoapp.response.ErrorResponse
import com.example.yougoapp.response.LoginResponse
import com.example.yougoapp.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
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
            // Handle HTTP exceptions (e.g., 404, 500, etc.)
            Log.e("HTTP Exception", e.message(), e)
            emit(State.Error("Login failed: ${e.message()}"))
        } catch (e: IOException) {

            Log.e("Network Exception", e.message, e)
            emit(State.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            // Handle other exceptions
            Log.e("Exception", e.message, e)
            emit(State.Error("An unexpected error occurred: ${e.message}"))
        }
    }

    fun getArticles():LiveData<State<List<ArticleItem>>> = liveData {
        emit(State.Loading)
        try {
            val pref = runBlocking {
                yogaPreference.getSession().first()
            }

            val article = apiService.getArticles()
            val articleList = article.data
            val articleMap = articleList.map { data->
                ArticleItem(
                    data.createdAt,
                    data.updateAt,
                    data.title,
                    data.imageUrl,
                    data.description,
                    data.id
                )
            }
            if (article.error==false){
                emit(State.Success(articleMap))
                Log.d("succes", articleMap.toString())
            }else{
                emit(State.Success(articleMap))
                Log.d("error",article.status.toString())
            }
        }catch (e : HttpException){
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
