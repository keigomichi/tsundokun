package com.example.tsundokun.ui.home.component.webPageList.webPageCard

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
 * htmlを取得
 */
@Composable
fun fetchHtml(url: String): String? {
    var html by remember { mutableStateOf<String?>(null) }
    var fetchError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = url) {
        withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(url).get()
                html = doc.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                fetchError = true
            }
        }
    }
    return html
}
