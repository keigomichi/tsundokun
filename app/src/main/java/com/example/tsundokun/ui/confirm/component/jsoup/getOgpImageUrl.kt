package com.example.tsundokun.ui.confirm.component.jsoup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

/*
 * htmlからogp画像のurlを取得
 */
@Composable
fun getOgpImageUrl(html: String?): String? {
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var fetchCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = html) {
        withContext(Dispatchers.IO) {
            try {
                val doc = html?.let { Jsoup.parse(it) }
                val ogImage = doc?.selectFirst("meta[property=og:image]")
                if (ogImage != null) {
                    imageUrl = ogImage.attr("content")
                }
                fetchCompleted = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    return if (fetchCompleted) imageUrl else null
}

