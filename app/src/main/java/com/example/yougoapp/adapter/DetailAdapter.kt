package com.example.yougoapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.ItemDetailBinding
import com.example.yougoapp.databinding.RekomendasiArticleBinding
import com.example.yougoapp.databinding.RekomendasiPoseBinding
import com.example.yougoapp.response.DetailItem
import com.example.yougoapp.response.PoseItem
import com.example.yougoapp.ui.pose.PlayActivity


class DetailAdapter(private val id: String, private var Detail: List<DetailItem>) :RecyclerView.Adapter<DetailAdapter.DetailViewHolder>(){
    inner  class DetailViewHolder(private  val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: DetailItem){
            binding.textView .text = data.step
            Glide.with(itemView.context).load(data.image)
                .into(binding.imageView)

            binding.itemLayout.setOnClickListener {
                val intent = Intent(itemView.context, PlayActivity::class.java)
                intent.putExtra(PlayActivity.EXTRA_ID,id)
                intent.putExtra(PlayActivity.EXTRA_IMAGE,data.image)
                intent.putExtra(PlayActivity.EXTRA_STEP,data.step)
                itemView.context.startActivity(intent)

            }
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailAdapter.DetailViewHolder {
        return DetailViewHolder(ItemDetailBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DetailAdapter.DetailViewHolder, position: Int) {
        val userData = Detail[position]
        holder.bind(userData)
    }

    override fun getItemCount(): Int {
        return  Detail.size
    }

}