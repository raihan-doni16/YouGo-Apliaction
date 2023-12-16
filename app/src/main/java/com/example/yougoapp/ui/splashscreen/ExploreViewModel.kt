package com.example.yougoapp.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.yougoapp.repository.YogaRepository

class ExploreViewModel(private val repository: YogaRepository): ViewModel() {
    fun getSession() = repository.getSession().asLiveData()
}