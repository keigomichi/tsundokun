package com.example.tsundokun.ui.home.component

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
 * htmlからfavicon画像のurlを取得
 */
@Composable
fun getFaviconImageUrl(html: String?): String? {
    var imageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = html) {
        withContext(Dispatchers.IO) {
            try {
                val doc = html?.let { Jsoup.parse(it) }
                val ogImage = doc?.selectFirst("[href~=.*\\.(ico|png)]")
                if (ogImage != null) {
                    imageUrl = ogImage.attr("href")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    return imageUrl
}
