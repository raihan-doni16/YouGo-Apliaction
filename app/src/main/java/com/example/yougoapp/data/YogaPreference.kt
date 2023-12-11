package com.example.yougoapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class YogaPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserData) {
        dataStore.edit { data ->
            data[USER_ID_KEY] = user.userId
            data[TOKEN_KEY] = user.token
            data[REFRESH_TOKEN_KEY] = user.refreshToken
            data[IS_LOADING_KEY] = true
        }
    }

    fun getSession(): Flow<UserData> {
        return dataStore.data.map { data ->
            UserData(
                data[USER_ID_KEY] ?: "",
                data[TOKEN_KEY] ?: "",
                data[REFRESH_TOKEN_KEY] ?: "",
                data[IS_LOADING_KEY] ?: false

            )
        }
    }

    suspend fun logout() {
        dataStore.edit { data ->
            data.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: YogaPreference? = null
        private val USER_ID_KEY = stringPreferencesKey("userId")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refreshToken")
        private val IS_LOADING_KEY = booleanPreferencesKey("IsLoading")

        fun getInstance(dataStore: DataStore<Preferences>): YogaPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = YogaPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}