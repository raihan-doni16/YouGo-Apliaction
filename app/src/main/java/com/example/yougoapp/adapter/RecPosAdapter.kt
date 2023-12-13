package com.example.yougoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.ItemPoseBinding
import com.example.yougoapp.databinding.RekomendasiPoseBinding
import com.example.yougoapp.response.Data

class RecPosAdapter(private  var poseData: List<Data>): RecyclerView.Adapter<RecPosAdapter.RecPoseViewHolder>() {
    inner  class  RecPoseViewHolder(private  val binding: RekomendasiPoseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Data){
            binding.tvItemName.text = data.title
            Glide.with(itemView.context).load(data.imageUrl)
                .into(binding.tvItemPhoto)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecPosAdapter.RecPoseViewHolder {
        return  RecPoseViewHolder(RekomendasiPoseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecPoseViewHolder, position: Int) {
        val data = poseData[position]
        holder.bind(data)
    }


    override fun getItemCount(): Int {
        return  poseData.size
    }
}