package com.example.yougoapp.data

import android.content.Context
import com.example.yougoapp.data.database.YogaDatabase
import com.example.yougoapp.network.ApiConfig
import com.example.yougoapp.repository.YogaRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun providerRepository(context: Context): YogaRepository {
        val pref = YogaPreference.getInstance(context.dataStore)
        val user = runBlocking {
            pref.getSession().first()
        }
        val database = YogaDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService(user.token)
        return YogaRepository.getInstance(pref, apiService, database.yogaDao())
    }

}