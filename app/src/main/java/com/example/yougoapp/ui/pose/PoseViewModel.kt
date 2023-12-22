package com.example.yougoapp.ui.pose

import androidx.lifecycle.ViewModel
import com.example.yougoapp.repository.YogaRepository

class PoseViewModel(private  val repository: YogaRepository):ViewModel() {
    fun getPose() = repository.getPose()
    fun detailPose(id: String) = repository.getDetailPose(id)
    fun addSchedule(id: Int, scheduleName: String, poseId: String, dayTime: String) =
        repository.addSchedule(id, scheduleName, poseId, dayTime)
}