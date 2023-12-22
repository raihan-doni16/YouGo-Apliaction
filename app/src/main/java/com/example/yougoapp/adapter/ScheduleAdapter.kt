package com.example.yougoapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.ItemHasilBinding
import com.example.yougoapp.databinding.ItemPoseBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.response.PoseItem
import com.example.yougoapp.ui.BMI.BmiViewModel
import com.example.yougoapp.ui.pose.DetailPoseActivity
import com.example.yougoapp.ui.schedule.ScheduleViewModel
import java.util.Locale


class ScheduleAdapter(private var poseData: List<scheduleItem>, private val onDeleteListener: (String) -> Unit) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>(), Filterable {


    private var filteredPose: List<scheduleItem> = poseData

    inner class ScheduleViewHolder(private val binding: ItemHasilBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: scheduleItem) {

            binding.tvItemTitle.text = data.poseId
            binding.tvItemTime.text = data.scheduleName
        }

    }
    fun deleteItem(position: Int) {
        val scheduleId = poseData[position].poseId
        onDeleteListener(scheduleId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ScheduleViewHolder {
        return ScheduleViewHolder(ItemHasilBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val data = filteredPose[position]
        holder.bind(data)
    }



    fun setData(newPose: List<scheduleItem>) {
        poseData = newPose
        filteredPose = newPose
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredPose.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<scheduleItem>()

                if (!constraint.isNullOrEmpty()) {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()

                    for (poseData in poseData) {
                        if (poseData.scheduleName?.toLowerCase(Locale.ROOT)?.contains(filterPattern) == true) {
                            filteredList.add(poseData)
                        }
                    }
                } else {
                    filteredList.addAll(poseData)
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredPose = results?.values as List<scheduleItem>
                notifyDataSetChanged()
            }
        }
    }
}