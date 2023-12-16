package com.example.yougoapp.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.yougoapp.R
import com.example.yougoapp.databinding.ActivityExploreBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.repository.YogaRepository
import com.example.yougoapp.ui.home.HomeActivity
import com.example.yougoapp.ui.home.HomeViewModel
import com.example.yougoapp.ui.login.LoginActivity

class ExploreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExploreBinding
    private val viewModel by viewModels<ExploreViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myButton.setOnClickListener {
            viewModel.getSession().observe(this) { user ->
                if (!user.isLogin) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                }
            }

        }

    }
}