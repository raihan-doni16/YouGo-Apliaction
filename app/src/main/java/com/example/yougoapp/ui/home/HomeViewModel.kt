package com.example.yougoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.yougoapp.repository.YogaRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: YogaRepository):ViewModel() {
    fun getSession() = repository.getSession().asLiveData()
    fun getPose() = repository.getPose()
    fun getArticle() = repository.getArticle()
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

}