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

@Composable
fun FetchHtml(url: String): String? {
    var html by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = url) {
        withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(url).get()
                html = doc.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    return html
}
