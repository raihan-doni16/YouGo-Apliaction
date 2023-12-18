package com.example.yougoapp.ui.detection

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.yougoapp.R
import com.example.yougoapp.databinding.ActivityResultBinding
import java.text.DecimalFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityResultBinding
    private var imageUri: Uri? = null
    companion object{
        const val  EXTRA_NAME = "name"
        const val EXTRA_AKURASI = "akurasi"
        const val  EXTRA_ISCORRECT = "isCorrect"
        const val  EXTRA_IMAGE = "image"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val akurasi = intent.getDoubleExtra(EXTRA_AKURASI,0.0)
        imageUri = intent?.getStringExtra(EXTRA_IMAGE)?.let { Uri.parse(it) }

        val cek = intent.getBooleanExtra(EXTRA_ISCORRECT,false)

        val decimalFormat = DecimalFormat("#.#")
        val formattedAkurasi = decimalFormat.format(akurasi)
        binding.akurasi.text = "$formattedAkurasi%"
        binding.name.text = name
        binding.ageTxt.text = cek.toString()
        imageUri?.let {
            binding.image.setImageURI(it)
        }



    }
}