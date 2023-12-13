package com.example.yougoapp.ui.pose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yougoapp.adapter.PoseAdapter
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.PoseFragmentBinding
import com.example.yougoapp.factory.ViewModelFactory


class PoseFragment : Fragment() {
    private lateinit var binding: PoseFragmentBinding
    private lateinit var adapter: PoseAdapter
    private val viewModel by viewModels<PoseViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = PoseFragmentBinding.inflate(inflater)


        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPose.layoutManager = layoutManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPose().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                when (data) {
                    is State.Loading -> {

                    }

                    is State.Success -> {
                        val pose = data.data
                        adapter = PoseAdapter(pose)
                        binding.rvPose.adapter = adapter
                    }
                    is State.Error ->{

                    }
                    else -> false
                }
            }
        }
    }


}