package com.example.yougoapp.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.yougoapp.R
import com.example.yougoapp.data.State
import com.example.yougoapp.data.UserData
import com.example.yougoapp.databinding.ActivityLoginBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.home.HomeActivity
import com.example.yougoapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private  lateinit var progressBar: ProgressBar
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        progressBar = binding.spinKit
        progressBar.isVisible = false
        setUp()
        actionLogin()
    }
    private  fun setUp(){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }


    private fun actionLogin() {
        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isEmpty()) {
                binding.loginEmail.error = getString(R.string.email_validation)
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.loginPassword.error = getString(R.string.password_validation)
                return@setOnClickListener
            }
            if (password.isNotEmpty() && email.isNotEmpty()) {
                viewModel.login(email, password).observe(this) { user ->
                    if (user != null) {
                        when (user) {
                            is State.Loading -> {
                                progressBar.isVisible = true
                            }

                            is State.Success -> {
                                Toast.makeText(this, "Login Sukses", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                                val dataUser = UserData(
                                    user.data.loginResult.userId ?:"",
                                    user.data.loginResult!!.accessToken,
                                    user.data.loginResult!!.refreshToken,
                                    true
                                )
                                viewModel.saveSession(dataUser)
                                finish()
                                onDestroy()
                            }

                            is State.Error -> {
                                Toast.makeText(this, user.error, Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }
            }
        }
    }
}