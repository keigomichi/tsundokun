package com.example.tsundokun.ui.home.component.webPageList.webPageCard

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun OpenWebView(url: String) {
    AndroidView(factory = { WebView(it) }) { webView ->
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
    }
}
