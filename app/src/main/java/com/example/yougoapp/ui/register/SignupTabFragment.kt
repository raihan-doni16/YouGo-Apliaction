package com.example.yougoapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.yougoapp.R
import com.example.yougoapp.databinding.FragmentSignupTabBinding


class SignupTabFragment: Fragment(R.layout.fragment_signup_tab) {

    private var _binding: FragmentSignupTabBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupTabBinding.bind(view)

    }
}