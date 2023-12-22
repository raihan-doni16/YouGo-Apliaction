package com.example.yougoapp.ui.schedule

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yougoapp.adapter.ScheduleAdapter
import com.example.yougoapp.adapter.scheduleItem
import com.example.yougoapp.databinding.ActivityScheduleBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.home.HomeActivity

class ScheduleActivity : AppCompatActivity() {
    private val viewModel by viewModels<ScheduleViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var adapter: ScheduleAdapter
    private lateinit var binding: ActivityScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ScheduleAdapter(emptyList()) { scheduleId ->
            viewModel.deleteSchedule(scheduleId)
        }

        val localAdapter = adapter

        binding.rvHasil.apply {
            layoutManager = LinearLayoutManager(this@ScheduleActivity)


            adapter = localAdapter


            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val position = viewHolder.adapterPosition

                    localAdapter.deleteItem(position)
                }
            })

            itemTouchHelper.attachToRecyclerView(binding.rvHasil)
        }

        binding.add.setOnClickListener {
            val intent = Intent(this, PickActivity::class.java)
            startActivity(intent)
        }

        viewModel.getSchedule()?.observe(this) { fav ->
            val item = mutableListOf<scheduleItem>()
            fav?.map {
                val items = scheduleItem(
                    scheduleName = it.scheduleName,
                    dayTime = it.dayTime,
                    poseId = it.poseId
                )
                item.add(items)
            }
            localAdapter.setData(item)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}