package com.example.yougoapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.ItemPoseBinding
import com.example.yougoapp.response.ArtikelItem
import com.example.yougoapp.response.Data
import com.example.yougoapp.response.PoseItem
import com.example.yougoapp.ui.article.DetailArticleActivity
import com.example.yougoapp.ui.pose.DetailPoseActivity
import java.util.Locale


class PoseAdapter(private var poseData: List<PoseItem>) :
    RecyclerView.Adapter<PoseAdapter.PoseViewHolder>(), Filterable {

    private var filteredPose: List<PoseItem> = poseData

    inner class PoseViewHolder(private val binding: ItemPoseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PoseItem) {

            binding.tvItemTitle.text = data.title
//            binding.tvItemTime.text = data.time.toString()
            Glide.with(itemView.context).load(data.imageUrl)
                .into(binding.tvItemPhoto)
            binding.itemLayout.setOnClickListener {
                val intent = Intent(itemView.context, DetailPoseActivity::class.java)
                intent.putExtra(DetailPoseActivity.EXTRA_ID,data.id)
                Log.d("cek data",data.id!!)
                intent.putExtra(DetailPoseActivity.EXTRA_PHOTO,data.imageUrl)
                intent.putExtra(DetailPoseActivity.EXTRA_TITLE,data.title)
                intent.putExtra(DetailPoseActivity.EXTRA_CATEGORY,data.category)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoseAdapter.PoseViewHolder {
        return PoseViewHolder(ItemPoseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PoseAdapter.PoseViewHolder, position: Int) {
        val data = filteredPose[position]
        holder.bind(data)
    }

    fun setData(newPose: List<PoseItem>) {
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
                val filteredList = mutableListOf<PoseItem>()

                if (!constraint.isNullOrEmpty()) {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()

                    for (poseData in poseData) {
                        if (poseData.title?.toLowerCase(Locale.ROOT)?.contains(filterPattern) == true) {
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
                filteredPose = results?.values as List<PoseItem>
                notifyDataSetChanged()
            }
        }
    }
}