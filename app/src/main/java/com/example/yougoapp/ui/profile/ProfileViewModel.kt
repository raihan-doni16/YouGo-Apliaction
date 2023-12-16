package com.example.yougoapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yougoapp.repository.YogaRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: YogaRepository) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}