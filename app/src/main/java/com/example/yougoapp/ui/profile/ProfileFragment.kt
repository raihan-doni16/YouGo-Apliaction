package com.example.yougoapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.FragmentProfileBinding
import com.example.yougoapp.factory.ViewModelFactory


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {

        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        binding.buttonAddProfile.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            startActivity(intent)
        }


        viewModel.getProfile().observe(viewLifecycleOwner) { prof ->
            if (prof != null) {
                when (prof) {
                    is State.Loading -> {

                    }

                    is State.Success -> {

                        val firstName = prof.data.profile.firstName
                        val lastName = prof.data.profile.lastName
                        binding.tvName.text = "$firstName $lastName"
                        binding.edBerat.text = prof.data.profile.weight.toString().trim()
                        binding.edBmi.text = prof.data.profile.status
                        binding.edUmur.text = prof.data.profile.age.toString().trim()
                        binding.edEmail.text = prof.data.email
                        Glide.with(requireContext()).load(prof.data.profile.imageUrl)
                            .into(binding.circleImageView)


                    }

                    is State.Error -> {

                    }
                }
            }

        }


        binding.btnKeluar.setOnClickListener {
            viewModel.logout()
        }
        return binding.root

    }
}