package com.example.yougoapp.ui.article

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yougoapp.adapter.ArticleAdapter
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.ActivityArticleBinding
import com.example.yougoapp.factory.ViewModelFactory

class ArticleActivity : AppCompatActivity() {

    private  val  viewModel by viewModels<ArticleViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private  lateinit var  adapter: ArticleAdapter
    private  lateinit var  binding: ActivityArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = GridLayoutManager(this, 2)
        binding.gridRecyclerView.layoutManager = layoutManager



        viewModel.getArticle().observe(this){user ->
            when(user){
                is State.Loading ->{

                }
                is State.Success ->{
                    val data = user.data
                    adapter = ArticleAdapter(data)
                    binding.gridRecyclerView.adapter = adapter

                }
                is State.Error->{

                }
            }
        }
    }

}