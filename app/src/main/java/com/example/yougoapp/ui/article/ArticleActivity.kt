package com.example.yougoapp.ui.article

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yougoapp.R
import com.example.yougoapp.adapter.ArticleAdapter
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.ActivityArticleBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.response.ArtikelItem
import retrofit2.http.Query
import java.util.Locale

class ArticleActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    private val viewModel by viewModels<ArticleViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var searchView: SearchView
    private lateinit var adapter: ArticleAdapter
    private lateinit var binding: ActivityArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressBar = binding.spinKit
        progressBar.isVisible = false
        val layoutManager = GridLayoutManager(this, 2)
        binding.gridRecyclerView.layoutManager = layoutManager

        adapter = ArticleAdapter(emptyList())
        binding.gridRecyclerView.adapter = adapter

        searchView = findViewById(R.id.search_artikel)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        viewModel.getArticle().observe(this) { user ->
            when (user) {
                is State.Loading -> {
                    progressBar.isVisible = true
                }

                is State.Success -> {
                    val data = user.data
                    adapter.setData(data)


                }

                is State.Error -> {

                }

            }
        }
    }


}