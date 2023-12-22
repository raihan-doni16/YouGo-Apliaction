package com.example.yougoapp.ui.BMI

import androidx.lifecycle.ViewModel
import com.example.yougoapp.repository.YogaRepository

class BmiViewModel(private val repository: YogaRepository) : ViewModel() {
    fun getPose() = repository.getPose()
}