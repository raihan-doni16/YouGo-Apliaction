package com.example.yougoapp.ui.pose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.yougoapp.R
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.ActivityDetailPoseBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.response.DetailItem
import com.example.yougoapp.ui.detection.DetectionActivity


class DetailPoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPoseBinding
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftMillis: Long = 60000 // 1 minute, you can adjust this
    private var currentImageIndex = 0
    private val imageResources = listOf(
        R.drawable.home_yoga,
        R.drawable.yoga1,
        R.drawable.yoga2
        // Add more image resources as needed
    )

    companion object {
        const val EXTRA_PHOTO = "extraPhoto"
        const val EXTRA_ID = "extraID"
        const val EXTRA_CATEGORY = "extraCategory"
        const val EXTRA_TITLE = "extraTitle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize your views using the binding object
        val myImageView = binding.myImageView
        val myTimeView = binding.myTimeView
        val buttonStart = binding.buttonStart
        val buttonPause = binding.buttonPause
        val buttonStop = binding.buttonStop


        val id = intent.getStringExtra(EXTRA_ID)
        Log.d("cek", id?:"")
        binding.bottomScan.setOnClickListener {
            var intent = Intent(this@DetailPoseActivity, DetectionActivity::class.java)
            intent.putExtra(DetectionActivity.EXTRA_ID, id)
            startActivity(intent)
        }

        // Set initial image
        myImageView.setImageResource(imageResources[currentImageIndex])

        // Set up CountDownTimer
        countDownTimer = object : CountDownTimer(timeLeftMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftMillis = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                // Change the image when the timer finishes
                currentImageIndex = (currentImageIndex + 1) % imageResources.size
                myImageView.setImageResource(imageResources[currentImageIndex])
            }
        }

        // Button click listeners
        buttonStart.setOnClickListener {
            startTimer()
        }

        buttonPause.setOnClickListener {
            pauseTimer()
        }

        buttonStop.setOnClickListener {
            stopTimer()
        }
    }

    private fun startTimer() {
        countDownTimer.start()
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
        timeLeftMillis = 60000 // Reset timer to the initial value
        updateCountdownText()
    }

    private fun updateCountdownText() {
        val minutes = (timeLeftMillis / 1000) / 60
        val seconds = (timeLeftMillis / 1000) % 60
        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        binding.myTimeView.text = timeLeftFormatted
    }
}



