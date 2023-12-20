package com.example.yougoapp.ui.register

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
import com.example.yougoapp.databinding.ActivityRegisterBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
   private  lateinit var progressBar: ProgressBar
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
            progressBar = findViewById(R.id.spin_kit)
            progressBar.isVisible = false
        binding.textSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

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
        actionRegister()
    }

    fun actionRegister(){
        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()

            if (email.isEmpty()){
                binding.signupEmail.error = getString(R.string.email_validation)
                return@setOnClickListener
            }
            if(password.isEmpty()){
                binding.signupPassword.error = getString(R.string.password_validation)
                return@setOnClickListener
            }
            if (password.isNotEmpty() && email.isNotEmpty()){
                viewModel.register(email, password).observe(this){user ->
                    when(user){
                        is State.Loading ->{
                            progressBar.isVisible = false
                        }
                        is State.Success ->{
                            Toast.makeText(this, "Register Sukses", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        is State.Error ->{
                            Toast.makeText(this, user.error, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }
    }
}