package com.example.yougoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.ItemPoseBinding
import com.example.yougoapp.response.Data


class PoseAdapter(private  var poseData: List<Data>):RecyclerView.Adapter<PoseAdapter.PoseViewHolder>() {
   inner  class  PoseViewHolder(private  val binding: ItemPoseBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(data: Data){
           binding.tvItemName.text = data.title
           Glide.with(itemView.context).load(data.imageUrl)
               .into(binding.tvItemPhoto)
       }
   }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoseAdapter.PoseViewHolder {
      return  PoseViewHolder(ItemPoseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PoseAdapter.PoseViewHolder, position: Int) {
      val data = poseData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return  poseData.size
    }
}