package com.example.yougoapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.ItemPoseBinding
import com.example.yougoapp.databinding.RekomendasiPoseBinding
import com.example.yougoapp.response.PoseItem
import com.example.yougoapp.ui.pose.DetailPoseActivity

class BmiAdapter(private  var poseData: List<PoseItem>): RecyclerView.Adapter<BmiAdapter.BmiViewHolder>() {
    inner  class  BmiViewHolder(private  val binding: ItemPoseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: PoseItem){
            binding.tvItemTitle.text = data.title
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BmiAdapter.BmiViewHolder {
        return  BmiViewHolder(ItemPoseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BmiViewHolder, position: Int) {
        val data = poseData[position]
        holder.bind(data)
    }


    override fun getItemCount(): Int {
        return  poseData.size
    }
}