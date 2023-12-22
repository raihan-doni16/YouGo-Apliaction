package com.example.yougoapp.ui.schedule

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yougoapp.adapter.AddAdapter
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.ActivityPickBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.pose.PoseViewModel

class PickActivity : AppCompatActivity() {
    private lateinit var adapter: AddAdapter
    private lateinit var binding: ActivityPickBinding
    private val viewModel by viewModels<PoseViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = layoutManager
        adapter = AddAdapter(emptyList())
        binding.rv.adapter = adapter

        viewModel.getPose().observe(this) {
            when (it) {
                is State.Loading -> {

                }

                is State.Success -> {
                    adapter.setData(it.data)
                }

                is State.Error -> {

                }
            }
        }
    }
}