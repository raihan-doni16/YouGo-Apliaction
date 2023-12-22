package com.example.yougoapp.ui.article

import androidx.lifecycle.ViewModel
import com.example.yougoapp.repository.YogaRepository

class ArticleViewModel(private val repository: YogaRepository) : ViewModel() {
    fun getArticle() = repository.getArticle()
}