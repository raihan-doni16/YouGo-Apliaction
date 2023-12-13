package com.example.yougoapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.ItemArticleBinding
import com.example.yougoapp.databinding.RekomendasiArticleBinding
import com.example.yougoapp.response.ArticleItem
import com.example.yougoapp.response.ArtikelItem
import com.example.yougoapp.ui.article.DetailArticleActivity

class ArticleAdapter( private  var article: List<ArtikelItem>):RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ArtikelItem) {
            binding.tvTitle.text = data.title
            Glide.with(itemView.context).load(data.imageUrl)
                .into(binding.photoArticle)

            binding.itemArticles.setOnClickListener {
                val intent = Intent(itemView.context,DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.EXTRA_TITLE,data.title)
                intent.putExtra(DetailArticleActivity.EXTRA_DESCRIPTION,data.description)
                intent.putExtra(DetailArticleActivity.EXTRA_PHOTO,data.imageUrl)
                intent.putExtra(DetailArticleActivity.EXTRA_UPDATE, data.updateAt)
                intent.putExtra(DetailArticleActivity.EXTRA_CREATED, data.createdAt)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleAdapter.ArticleViewHolder {
        return ArticleViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return  article.size
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ArticleViewHolder, position: Int) {
        val userData = article[position]
        holder.bind(userData)
    }
}

