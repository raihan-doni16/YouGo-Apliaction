package com.example.yougoapp.ui.BMI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yougoapp.R
import com.example.yougoapp.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}