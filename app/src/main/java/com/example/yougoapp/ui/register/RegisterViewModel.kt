package com.example.yougoapp.ui.register

import androidx.lifecycle.ViewModel
import com.example.yougoapp.repository.YogaRepository

class RegisterViewModel(private val yogaRepository: YogaRepository) : ViewModel() {
    fun register(email: String, password: String) = yogaRepository.register(email, password)
}