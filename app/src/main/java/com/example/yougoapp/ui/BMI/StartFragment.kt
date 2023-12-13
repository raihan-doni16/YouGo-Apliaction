package com.example.yougoapp.ui.BMI

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.yougoapp.R
import com.example.yougoapp.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    private var height: Float = 0f
    private var weight: Float = 0f
    private var countWeight = 50
    private var countAge = 19
    private var chose: Boolean = true
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)

        component()

        return binding.root
    }

    private fun component() {
        binding.apply {
            Seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, prgress: Int, fromUser: Boolean) {
                    val ht = prgress.toString() + resources.getString(R.string._0cm)
                    binding.heightTxt.text = ht
                    height = prgress.toFloat() / 100
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
            weightPlus.setOnClickListener {
                binding.weightTxt.text = countWeight++.toString()
            }
            weightMinus.setOnClickListener {
                binding.weightTxt.text = countWeight--.toString()
            }
            agePlus.setOnClickListener {
                binding.age.text = countAge++.toString()
            }
            ageMinus.setOnClickListener {
                binding.age.text = countAge--.toString()
            }

            calculate.setOnClickListener {
                if (!chose) {
                    if (height.equals(0f)) {
                        Toast.makeText(
                            requireContext(),
                            "Height cannot be zero",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        weight = binding.weightTxt.text.toString().toFloat()
                        calculateBMI(age.text.toString())
                    }
                } else {
                    Toast.makeText(requireContext(), "Choose Your Gender", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            cardViewMale.setOnClickListener {
                if (chose) {
                    maleTxt.setTextColor(Color.parseColor("#8eb8fa"))
                    maleTxt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.man_blue, 0, 0)
                    cardViewFemale.isEnabled = false
                    chose = false
                } else {
                    maleTxt.setTextColor(Color.parseColor("#8D8E99"))
                    maleTxt.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        R.drawable.baseline_man_24,
                        0,
                        0
                    )
                    cardViewFemale.isEnabled = true
                    chose = true
                }
            }
            cardViewFemale.setOnClickListener {
                if (chose) {
                    femaleTxt.setTextColor(Color.parseColor("#8eb8fa"))
                    femaleTxt.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        R.drawable.women_blue,
                        0,
                        0
                    )
                    cardViewMale.isEnabled = false
                    chose = false
                } else {
                    maleTxt.setTextColor(Color.parseColor("#8D8E99"))
                    maleTxt.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        R.drawable.baseline_woman_24,
                        0,
                        0
                    )
                    cardViewMale.isEnabled = true
                    chose = true
                }


            }
        }



    }
    private fun calculateBMI(age: String) {
        val bmi = weight / (height*height)
        val action = StartFragmentDirections.actionHomeFragmentToResultFragment(bmi,age)
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}