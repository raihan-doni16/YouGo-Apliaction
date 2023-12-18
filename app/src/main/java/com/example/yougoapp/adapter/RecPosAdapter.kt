package com.example.yougoapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.ItemPoseBinding
import com.example.yougoapp.databinding.RekomendasiArticleBinding
import com.example.yougoapp.databinding.RekomendasiPoseBinding
import com.example.yougoapp.response.Data
import com.example.yougoapp.response.PoseItem
import com.example.yougoapp.ui.pose.DetailPoseActivity

class RecPosAdapter(private  var poseData: List<PoseItem>): RecyclerView.Adapter<RecPosAdapter.RecPoseViewHolder>() {
    inner  class  RecPoseViewHolder(private  val binding: RekomendasiPoseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: PoseItem){
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