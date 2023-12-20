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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.yougoapp.R
import com.example.yougoapp.adapter.DetailAdapter
import com.example.yougoapp.adapter.RecArtAdapter
import com.example.yougoapp.adapter.RecPosAdapter
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.ActivityDetailPoseBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.response.DetailItem
import com.example.yougoapp.ui.detection.DetectionActivity


class DetailPoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPoseBinding
    private val viewModel by viewModels<PoseViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private  lateinit var adapter: DetailAdapter


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

        val layoutManager = LinearLayoutManager(this)
        binding.rvDetail.layoutManager= layoutManager

        val image  = intent.getStringExtra(EXTRA_PHOTO)
        val title  = intent.getStringExtra(EXTRA_TITLE)

        binding.title.text = title
        Glide.with(this).load(image).into(binding.myImageView)

        val id = intent.getStringExtra(EXTRA_ID)
        Log.d("cek", id?:"")
        binding.bottomScan.setOnClickListener {
            var intent = Intent(this@DetailPoseActivity, DetectionActivity::class.java)
            intent.putExtra(DetectionActivity.EXTRA_ID,id)
            startActivity(intent)
        }

        viewModel.detailPose(id?:"").observe(this){detail ->
            if (detail!=null){
                when(detail){
                    is State.Loading ->{

                    }
                    is State.Success ->{
                        val pose = detail.data.pose.detail
                        adapter = DetailAdapter(id?:"",pose)
                        binding.rvDetail.adapter = adapter
                    }
                    is State.Error ->{

                    }
                }
            }
        }


    }


}



