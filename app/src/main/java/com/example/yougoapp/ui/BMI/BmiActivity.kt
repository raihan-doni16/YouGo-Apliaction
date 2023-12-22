package com.example.yougoapp.ui.BMI

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yougoapp.databinding.ActivityBmiBinding
import com.example.yougoapp.ui.home.HomeActivity

class BmiActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}