// package com.example.tsundokun.ui.home.component.webPageList
//
// import androidx.compose.foundation.layout.Column
// import androidx.compose.material3.Tab
// import androidx.compose.material3.TabRow
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.remember
// import androidx.compose.runtime.saveable.rememberSaveable
// import androidx.compose.runtime.setValue
// import com.example.tsundokun.data.local.entities.TsundokuEntity
// import com.example.tsundokun.ui.home.category.tab.AddTabTitleDialog
// import com.example.tsundokun.ui.home.component.data.Screen
// import com.example.tsundokun.ui.home.component.data.Screen.ALL
// import com.example.tsundokun.ui.home.component.data.Screen.FAVORITE
// import com.example.tsundokun.ui.home.component.data.WebPage
// import com.example.tsundokun.ui.home.component.webPageList.webPageCard.fetchHtml
// import com.example.tsundokun.ui.home.component.webPageList.webPageCard.getFaviconImageUrl
// import com.example.tsundokun.ui.home.component.webPageList.webPageCard.getOgpImageUrl
// import com.example.tsundokun.ui.home.component.webPageList.webPageCard.getTitle
// import com.ramcosta.composedestinations.navigation.DestinationsNavigator
//
// /*
// * Webページのリスト
// * タブで表示を切り替えられる
// */
// @Composable
// fun WebPageListScreen(tsundokuEntityList: List<TsundokuEntity>, navigator: DestinationsNavigator) {
//    var tabName by remember {
//        mutableStateOf(
//            mutableListOf("すべて", "お気に入り"),
//        )
//    }
//
//    var tabSelected by rememberSaveable { mutableStateOf(ALL) }
//
//    val tsundokuItem = tsundokuEntityList.map {
//        WebPage(
//            getTitle(html = fetchHtml(url = it.link)),
//            getOgpImageUrl(html = fetchHtml(url = it.link)),
//            getFaviconImageUrl(html = fetchHtml(url = it.link)),
//            it.link,
//            it.isFavorite,
//            it.id,
//        )
//    }
//
//    val showDialog = remember { mutableStateOf(false) }
//    Column {
//        TabRow(
//            selectedTabIndex = tabSelected.ordinal,
//        ) {
//            tabName.forEachIndexed { index, _ ->
//                Tab(
//                    text = { Text(text = tabName[index]) },
//                    selected = tabSelected.ordinal == index,
//                    onClick = { tabSelected = Screen.values()[index] },
//                )
//            }
//            Tab(
//                text = { Text("+") },
//                selected = false,
//                onClick = { showDialog.value = true },
//            )
//        }
//        val allTsundokus = tsundokuItem
//        val favoriteTsundoku = allTsundokus.filter { it.isFavorite }
//
//        when (tabSelected) {
//            ALL -> WebPageList(webPageList = allTsundokus, navigator = navigator)
//            FAVORITE -> WebPageList(webPageList = favoriteTsundoku, navigator = navigator)
//        }
//    }
//    if (showDialog.value) AddTabTitleDialog(setShowDialog = { showDialog.value = it }, tabList = tabName)
// }
