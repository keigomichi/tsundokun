package com.example.tsundokun.ui.home.category.tab

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.tsundokun.data.local.entities.CategoryEntity
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.domain.models.Tsundoku
import com.example.tsundokun.ui.home.component.webPageList.TsundokuListScreen
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
@Composable
fun CategoryTab(navigator: DestinationsNavigator, categories: List<CategoryEntity>, webPageList: List<Tsundoku>) {
    var tabSelected by rememberSaveable { mutableStateOf(categories[0]) }

        ScrollableTabRow(
        selectedTabIndex = categories.indexOf(tabSelected),
    ) {
        categories.forEachIndexed { index, _ ->
            Tab(
                text = { Text(text = categories[index].label) },
                selected = tabSelected == categories[index],
                onClick = { tabSelected = categories[index] },
            )
        }
            Tab(selected = false,
                text = { Text(text = "＋") },
                onClick = {null}
            )
        }
}