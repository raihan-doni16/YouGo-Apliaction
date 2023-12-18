package com.example.yougoapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yougoapp.databinding.ItemArticleBinding
import com.example.yougoapp.databinding.ItemPoseBinding
import com.example.yougoapp.databinding.RekomendasiArticleBinding
import com.example.yougoapp.response.ArticleItem
import com.example.yougoapp.response.ArtikelItem
import com.example.yougoapp.response.Data
import com.example.yougoapp.ui.article.DetailArticleActivity
import java.util.Locale

class ArticleAdapter(private var article: List<ArtikelItem>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(), Filterable {

    private var filteredArticles: List<ArtikelItem> = article

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ArtikelItem) {
            binding.tvTitle.text = data.title

            Glide.with(itemView.context).load(data.imageUrl)
                .into(binding.photoArticle)

            binding.itemLayout.setOnClickListener {
                val intent = Intent(itemView.context, DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.EXTRA_TITLE, data.title)
                intent.putExtra(DetailArticleActivity.EXTRA_DESCRIPTION, data.description)
                intent.putExtra(DetailArticleActivity.EXTRA_PHOTO, data.imageUrl)
                intent.putExtra(DetailArticleActivity.EXTRA_UPDATE, data.updateAt)
                intent.putExtra(DetailArticleActivity.EXTRA_WEB_URL, data.webUrl)
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
        return filteredArticles.size
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ArticleViewHolder, position: Int) {
        val userData = filteredArticles[position]
        holder.bind(userData)
    }

    fun setData(newArticle: List<ArtikelItem>) {
        article = newArticle

        filteredArticles = newArticle
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<ArtikelItem>()

                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(article)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()

                    for (article in article) {
                        if (article.title?.toLowerCase(Locale.ROOT)?.contains(filterPattern) == true) {
                            filteredList.add(article)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredArticles = results?.values as List<ArtikelItem>
                notifyDataSetChanged()
            }
        }
    }
}

