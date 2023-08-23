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
 * htmlからタイトルを取得
 */
@Composable
fun getTitle(html: String?): String? {
    var title by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = html) {
        withContext(Dispatchers.IO) {
            try {
                val doc = html?.let { Jsoup.parse(it) }
                if (doc != null) {
                    title = doc.title()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    return title
}
