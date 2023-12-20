package com.example.yougoapp.ui.pose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yougoapp.adapter.PoseAdapter
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.PoseFragmentBinding
import com.example.yougoapp.factory.ViewModelFactory


class PoseFragment : Fragment() {
    private  lateinit var  progressBar: ProgressBar
    private lateinit var binding: PoseFragmentBinding
    private lateinit var adapter: PoseAdapter
    private  lateinit var searchView: SearchView
    private val viewModel by viewModels<PoseViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = PoseFragmentBinding.inflate(inflater)

             progressBar = binding.spinKit
        progressBar.isVisible =true
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPose.layoutManager = layoutManager
        adapter = PoseAdapter(emptyList())
        binding.rvPose.adapter =adapter
        searchView = binding.searchPose

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPose().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                when (data) {
                    is State.Loading -> {
                        progressBar.isVisible = false
                    }

                    is State.Success -> {
                        val pose = data.data
                        adapter.setData(pose)

                    }
                    is State.Error ->{

                    }
                    else -> false
                }
            }
        }
    }


}