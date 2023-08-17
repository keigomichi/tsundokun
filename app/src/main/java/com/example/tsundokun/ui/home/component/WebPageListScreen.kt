package com.example.tsundokun.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.ui.home.component.Screen.ALL
import com.example.tsundokun.ui.home.component.Screen.FAVORITE
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/*
 * Webページのリスト
 * タブで表示を切り替えられる
 */
@Composable
fun WebPageListScreen(tsundokuEntityList: List<TsundokuEntity>, navigator: DestinationsNavigator) {
    var tabName by remember {
        mutableStateOf(
            mutableListOf("すべて", "お気に入り"),
        )
    }

    var tabSelected by rememberSaveable { mutableStateOf(ALL) }

    val tsundokuItem = tsundokuEntityList.map {
        WebPage(
            getTitle(html = fetchHtml(url = it.link)),
            getOgpImageUrl(html = fetchHtml(url = it.link)),
            getFaviconImageUrl(html = fetchHtml(url = it.link)),
            it.link,
        )
    }

    val showDialog = remember { mutableStateOf(false) }
    Column {
        TabRow(
            selectedTabIndex = tabSelected.ordinal,
        ) {
            tabName.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = tabName[index].toString()) },
                    selected = tabSelected.ordinal == index,
                    onClick = { tabSelected = Screen.values()[index] },

                    )
            }
            Tab(
                text = { Text("+") },
                selected = false,
                onClick = { showDialog.value = true },
            )
        }

        /* 以下は一時的に表示するダミーの情報 */
        val allTsundokus = tsundokuItem.reversed()

        var favoriteTsundoku = listOf<WebPage>(
            WebPage(
                getTitle(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getOgpImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getFaviconImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                "https://www.yahoo.co.jp/",
            ),
            WebPage(
                getTitle(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getOgpImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getFaviconImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                "https://www.yahoo.co.jp/",
            ),
            WebPage(
                getTitle(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getOgpImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getFaviconImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                "https://www.yahoo.co.jp/",
            ),
            WebPage(
                getTitle(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getOgpImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                getFaviconImageUrl(html = fetchHtml(url = "https://www.yahoo.co.jp/")),
                "https://www.yahoo.co.jp/",
            ),
        )
        when (tabSelected) {
            ALL -> WebPageListCard(webPageList = allTsundokus, navigator = navigator)
            FAVORITE -> WebPageListCard(webPageList = favoriteTsundoku, navigator = navigator)
        }
    }
    if (showDialog.value) AddTabTitleDialog(setShowDialog = { showDialog.value = it }, tabList = tabName)
}
