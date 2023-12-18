package com.example.yougoapp.ui.profile

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.yougoapp.R
import com.example.yougoapp.databinding.ActivityEditBinding
import com.example.yougoapp.databinding.FragmentProfileBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.reduceFileImage
import com.example.yougoapp.ui.uriToFile

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private val viewModel by viewModels<ProfileViewModel> {

        ViewModelFactory.getInstance(this)
    }
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email = binding.edEmail.text
        val firstName = binding.edFirstName.text
        val lastName = binding.edLastName.text
        val age = binding.edUmur.text
        val weight = binding.edBerat.text
        val height = binding.edTinggi.text

        binding.btnSave.setOnClickListener {
            imageUri?.let { uri ->
                val image = uriToFile(uri, this).reduceFileImage()
                viewModel.postProfile(
                    email.toString(),
                    firstName.toString(),
                    lastName.toString(),
                    age.toString(),
                    weight.toString(),
                    height.toString(),
                    image,
                ).observe(this) {
                    finish()
                }
            }
        }
        binding.editPhoto.setOnClickListener {
            startGallery()
        }
    }
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                showImage()
            }
        }

    private fun showImage() {
        imageUri?.let {
            Log.d("Image URL", "ShowImage: $it")
            binding.circleImageView.setImageURI(it)
        }
    }
}