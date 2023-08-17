package com.example.tsundokun.ui.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/*
 * Webページのリスト(Lazy Column)の作成
 */
@Composable
fun WebPageListCard(webPageList: List<WebPage>, modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
    LazyColumn(modifier = modifier) {
        items(webPageList) { webPage ->
            WebPageCard(
                webpage = webPage,
                modifier = Modifier.padding(8.dp),
                navigator = navigator,
            )
            Divider(color = Color.Gray) // 区切り線
        }
    }
}

