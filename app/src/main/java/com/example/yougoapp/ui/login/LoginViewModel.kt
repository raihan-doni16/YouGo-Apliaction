package com.example.yougoapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yougoapp.data.UserData
import com.example.yougoapp.repository.YogaRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: YogaRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)

    fun saveSession(user: UserData) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}