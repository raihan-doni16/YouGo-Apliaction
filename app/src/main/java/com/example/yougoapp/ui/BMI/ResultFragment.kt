package com.example.yougoapp.ui.BMI

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.util.Util
import com.example.yougoapp.R
import com.example.yougoapp.databinding.FragmentResultBinding
import com.example.yougoapp.ui.Utils
import kotlin.math.roundToInt


class ResultFragment : Fragment() {

    private  var _binding: FragmentResultBinding?= null
    private val binding  get() = _binding!!
    private  val args: ResultFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater,container,false)
        val bmi =(args.bmi *100 /100).roundToInt().toFloat()
        val age = args.age

        binding.apply {
            yourBmi.text = bmi.toString()
            condition.text = Utils.checkAdult(age.toInt(),bmi)
            recalculate.setOnClickListener {
                requireActivity().onBackPressed()
            }

        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}