package com.example.yougoapp.ui.pose

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.yougoapp.R
import com.example.yougoapp.databinding.ActivityDetailPoseBinding
import com.example.yougoapp.ui.article.DetailArticleActivity
import com.example.yougoapp.ui.detection.DetectionActivity

class DetailPoseActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityDetailPoseBinding
    companion object{
        const val EXTRA_PHOTO = "extraPhoto"
        const val EXTRA_ID = "extraID"
        const val EXTRA_CATEGORY = "extraCategory"
        const val EXTRA_TITLE ="extraTitle"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPoseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val id = intent.getStringExtra(EXTRA_ID)
        val photo = intent.getStringExtra(EXTRA_PHOTO)
        val category =intent.getStringExtra(EXTRA_CATEGORY)
            Log.d("error",id ?: "id null")



        binding.bottomScan.setOnClickListener {
            val intent = Intent(this, DetectionActivity::class.java)
            intent.putExtra(DetectionActivity.EXTRA_ID, id )
            startActivity(intent)
        }



    }
}



