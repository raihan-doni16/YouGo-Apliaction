package com.example.yougoapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yougoapp.repository.YogaRepository
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel(private val repository: YogaRepository) : ViewModel() {
    fun getProfile() = repository.getProfile()
    fun postProfile(email: String, firstName: String, lastName: String, age: String, weight: String,  height: String, image: File) = repository.postProfile(email,firstName, lastName, age,weight,height,image)
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}