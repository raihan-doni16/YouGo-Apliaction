package com.example.yougoapp.ui.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.yougoapp.R
import com.example.yougoapp.databinding.FragmentEditProfileBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.reduceFileImage
import com.example.yougoapp.ui.uriToFile


class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater)
        val email = binding.edEmail.text
        val firstName = binding.edFirstName.text
        val lastName = binding.edLastName.text
        val age = binding.edUmur.text
        val weight = binding.edBerat.text
        val height = binding.edTinggi.text

        binding.btnSave.setOnClickListener {
            imageUri?.let { uri ->
                val image = uriToFile(uri, requireContext()).reduceFileImage()
                viewModel.postProfile(
                    email.toString(),
                    firstName.toString(),
                    lastName.toString(),
                    age.toString(),
                    weight.toString(),
                    height.toString(),
                    image
                ).observe(viewLifecycleOwner) {
                }
            }
        }
        binding.editPhoto.setOnClickListener {
            startGallery()
        }
        return binding.root
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