package com.example.yougoapp.ui.BMI

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.util.Util
import com.example.yougoapp.R
import com.example.yougoapp.adapter.BmiAdapter
import com.example.yougoapp.adapter.RecArtAdapter
import com.example.yougoapp.adapter.RecPosAdapter
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.FragmentResultBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.Utils
import com.example.yougoapp.ui.home.HomeViewModel
import kotlin.math.roundToInt


class ResultFragment : Fragment() {
    private  lateinit var adapter: BmiAdapter
    private val viewModel by viewModels<BmiViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private  var _binding: FragmentResultBinding?= null
    private val binding  get() = _binding!!
    private  val args: ResultFragmentArgs by navArgs()
    private  lateinit var  progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater,container,false)
        val bmi =(args.bmi *100 /100).roundToInt().toFloat()
        val age = args.age
        progressBar = binding.spinKit
        progressBar.isVisible = true
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPose.layoutManager= layoutManager
        binding.apply {

            yourBmi.text = bmi.toString()
            condition.text = Utils.checkAdult(age.toInt(),bmi)
            recalculate.setOnClickListener {
                requireActivity().onBackPressed()
            }
            viewModel.getPose().observe(viewLifecycleOwner){data ->
                if (data != null){
                    when(data){
                        is State.Loading ->{
                            progressBar.isVisible = false
                        }
                        is State.Success ->{
                            val pose = data.data
                            val filteredPose = pose.filter { item ->

                                item.category == Utils.checkAdult(age.toInt(), bmi)
                            }

                            adapter = BmiAdapter(filteredPose)
                            binding.rvPose.adapter = adapter
                            condition.text = Utils.checkAdult(age.toInt(), bmi)

                        }
                        is  State.Error ->{

                        }
                    }
                }
            }

        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}