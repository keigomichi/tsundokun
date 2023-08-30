package com.example.tsundokun.ui.home.category.tab

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.tsundokun.domain.models.Category
import com.example.tsundokun.domain.models.Tsundoku
import com.example.tsundokun.ui.home.component.webPageList.TsundokuListScreen
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.example.tsundokun.ui.home.component.data.Screen
import com.example.tsundokun.ui.home.component.data.Screen.ALL

@Composable
fun CategoryTab(
    navigator: DestinationsNavigator,
    categories: List<Category>,
    webPageList: List<Tsundoku>
) {

    var tabName by remember {
        mutableStateOf(
            mutableListOf("すべて", "お気に入り"),
        )
    }

    var tabSelected by rememberSaveable { mutableStateOf(ALL) }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    ScrollableTabRow(
        selectedTabIndex = tabSelected.ordinal,
    ) {
        categories.forEachIndexed { index, _ ->
            Tab(
                text = { Text(text = categories[index].label) },
                selected = tabSelected.ordinal == index,
                onClick = { tabSelected = Screen.values()[index] },
            )
        }
        Tab(selected = false,
            text = { Text(text = "＋") },
            onClick = {
                showDialog.value = true
            }
        )
    }
    if (showDialog.value) {
        AddTabTitleDialog(
            setShowDialog = { showDialog.value = it },
            tabList = tabName,
        )
    }
    when (tabSelected) {
        ALL -> {
            TsundokuListScreen(selectedCategoryTsundokuList = webPageList, navigator = navigator)
        }

        Screen.FAVORITE -> {
            TsundokuListScreen(
                selectedCategoryTsundokuList = webPageList.filter { it.isFavorite },
                navigator = navigator
            )
        }

        else -> {
            TsundokuListScreen(
                selectedCategoryTsundokuList = webPageList
//                .filter { it.categoryId.equals(tabSelected) }
                , navigator = navigator
            )
        }
    }
    TsundokuListScreen(selectedCategoryTsundokuList = webPageList, navigator = navigator)
}