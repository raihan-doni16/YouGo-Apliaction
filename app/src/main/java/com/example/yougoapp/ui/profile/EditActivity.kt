package com.example.yougoapp.ui.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.yougoapp.R
import com.example.yougoapp.databinding.ActivityEditBinding
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

        supportFragmentManager.beginTransaction().remove(ProfileFragment()).commit()
        binding.btnSave.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val firstName = binding.edFirstName.text.toString().removeSurrounding("\"").trim()
            val lastName = binding.edLastName.text.toString().removeSurrounding("\"").trim()
            val age = binding.edUmur.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull()
            val weight = binding.edBerat.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull()
            val height = binding.edTinggi.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull()

            imageUri?.let { uri ->
                val image = uriToFile(uri, this).reduceFileImage()
                viewModel.postProfile(
                    email,
                    firstName,
                    lastName,
                    age ?: 0,
                    weight ?: 0,
                    height ?: 0,
                    image
                ).observe(this) {

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

    override fun onBackPressed() {
        super.onBackPressed()

    }

    fun button() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fram, ProfileFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}