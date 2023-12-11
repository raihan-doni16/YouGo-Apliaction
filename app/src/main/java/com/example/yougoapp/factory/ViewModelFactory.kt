package com.example.yougoapp.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yougoapp.data.Injection
import com.example.yougoapp.repository.YogaRepository
import com.example.yougoapp.ui.article.ArticleViewModel
import com.example.yougoapp.ui.home.HomeViewModel
import com.example.yougoapp.ui.login.LoginViewModel
import com.example.yougoapp.ui.register.RegisterViewModel

class ViewModelFactory(private val repository: YogaRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(LoginViewModel::class.java)->{
                LoginViewModel(repository)as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java)->{
                RegisterViewModel(repository) as  T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java)->{
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ArticleViewModel::class.java)->{
                ArticleViewModel(repository)as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: "+ modelClass.name)

        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.providerRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}