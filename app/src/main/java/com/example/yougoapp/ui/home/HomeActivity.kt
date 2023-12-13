package com.example.yougoapp.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.yougoapp.R
import com.example.yougoapp.databinding.ActivityMainBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.BMI.BmiActivity
import com.example.yougoapp.ui.login.LoginActivity
import com.example.yougoapp.ui.pose.PoseFragment
import com.example.yougoapp.ui.profile.ProfileFragment


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_nav -> replaceFragment(HomeFragment())
                R.id.pose_nav -> replaceFragment(PoseFragment())
                R.id.article_nav -> {
                    val intent = Intent(this, BmiActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.profile_nav -> replaceFragment(ProfileFragment())
                else -> false
            }
            true
        }

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        setUp()
    }

    private fun setUp() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManeger = supportFragmentManager
        val fragmentTransaction = fragmentManeger.beginTransaction()
        fragmentTransaction.replace(R.id.navHost, fragment)
        fragmentTransaction.commit()
    }

}