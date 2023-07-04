package com.example.repconnectjetpackcompose.activities

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.repconnectjetpackcompose.ui.theme.RepConnectJetpackComposeTheme

class WebViewActivity : ComponentActivity() {
    var url :String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
        setContent {
            url?.let { loadWebUrl(url = it) }
        }

    }
    private fun getIntentData() {
        url = intent.getStringExtra("url")
    }
}

@Composable
fun loadWebUrl(url: String) {

    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()

            loadUrl(url)
        }
    })
}