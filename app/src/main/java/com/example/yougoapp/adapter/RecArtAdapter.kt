package com.example.yougoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.RekomendasiArticleBinding
import com.example.yougoapp.response.ArtikelItem

class RecArtAdapter ( private  var article: List<ArtikelItem>):RecyclerView.Adapter<RecArtAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding: RekomendasiArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ArtikelItem) {
            binding.tvItemName .text = data.title
            Glide.with(itemView.context).load(data.imageUrl)
                .into(binding.tvItemPhoto)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecArtAdapter.ArticleViewHolder {
        return ArticleViewHolder(RekomendasiArticleBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val userData = article[position]
        holder.bind(userData)
    }

    override fun getItemCount(): Int {
        return  article.size
    }


}