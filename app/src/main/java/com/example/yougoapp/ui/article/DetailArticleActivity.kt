package com.example.yougoapp.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.yougoapp.R
import com.example.yougoapp.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    companion object{
        val EXTRA_PHOTO = "extraPhoto"
        val EXTRA_TITLE = "extraName"
        val EXTRA_DESCRIPTION = "extraDescription"
        val EXTRA_CREATED ="extraCreated"
        val EXTRA_UPDATE ="extraUpdate"
    }
    private lateinit var binding: ActivityDetailArticleBinding
    private lateinit var  webView : WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val photo = intent.getStringExtra(EXTRA_PHOTO)
        val created =intent.getStringExtra(EXTRA_CREATED)
        val  update = intent.getStringExtra(EXTRA_UPDATE)

        webView = findViewById(R.id.web_view)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://raihan-doni16.github.io/")

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

    }
}