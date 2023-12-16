package com.example.yougoapp.ui.detection

import androidx.lifecycle.ViewModel
import com.example.yougoapp.repository.YogaRepository
import java.io.File

class DetectionViewModel (private  val userRepository: YogaRepository): ViewModel(){
    fun checkMyDetection(id: String, image: File) = userRepository.detection(id,image)
}