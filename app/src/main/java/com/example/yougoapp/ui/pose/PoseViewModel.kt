package com.example.yougoapp.ui.pose

import androidx.lifecycle.ViewModel
import com.example.yougoapp.repository.YogaRepository

class PoseViewModel(private  val repository: YogaRepository):ViewModel() {
    fun getPose() = repository.getPose()
}