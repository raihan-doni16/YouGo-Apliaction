package com.example.yougoapp.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yougoapp.repository.YogaRepository
import kotlinx.coroutines.launch

class ScheduleViewModel(private var repository: YogaRepository) : ViewModel() {
    fun deleteSchedule(scheduleId: String) {
        viewModelScope.launch {
            repository.deleteSchedule(scheduleId)
        }
    }

    fun getSchedule() = repository.getSchedule()

}