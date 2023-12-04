package com.example.yougoapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.yougoapp.R
import com.example.yougoapp.databinding.FragmentLoginTabBinding

class LoginTabFragment: Fragment(R.layout.fragment_login_tab) {

    private var _binding: FragmentLoginTabBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginTabBinding.bind(view)

    }
}
