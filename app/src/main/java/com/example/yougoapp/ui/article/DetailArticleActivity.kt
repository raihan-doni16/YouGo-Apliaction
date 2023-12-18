package com.example.yougoapp.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.yougoapp.R
import com.example.yougoapp.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_PHOTO = "extraPhoto"
        const val EXTRA_TITLE = "extraName"
        const val EXTRA_DESCRIPTION = "extraDescription"
        const val EXTRA_CREATED ="extraCreated"
        const val EXTRA_UPDATE ="extraUpdate"
        const val EXTRA_WEB_URL = "extraWebUrl"
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
        val web = intent.getStringExtra(EXTRA_WEB_URL)

        webView = findViewById(R.id.web_view)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(web?:"")

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

    }
}