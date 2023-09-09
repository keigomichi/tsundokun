package com.example.tsundokun.ui.home.category.tab

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.tsundokun.ui.home.HomeViewModel.HomeUiState
import com.example.tsundokun.ui.home.component.webPageList.TsundokuListScreen
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun CategoryTab(
    navigator: DestinationsNavigator,
    uiState: HomeUiState,
    onTabClick: (Int) -> Unit,
    onAddTabClick: (String) -> Unit,
) {
    val showDialog = rememberSaveable { mutableStateOf(false) }
    ScrollableTabRow(
        selectedTabIndex = uiState.category.map { it.id }.indexOf(uiState.selectedCategory.id),
    ) {
        uiState.category.forEachIndexed { index, _ ->
            Tab(
                text = { Text(text = uiState.category[index].label) },
                selected = uiState.selectedCategory.id == uiState.category[index].id,
                onClick = { onTabClick(index) },
            )
        }
        Tab(
            selected = false,
            text = { Text(text = "＋") },
            onClick = {
                showDialog.value = true
            },
        )
    }
    if (showDialog.value) {
        AddTabTitleDialog(
            setShowDialog = { showDialog.value = it },
            onAddTabClick = { onAddTabClick(it) },
        )
    }
    when (uiState.selectedCategory.id) {
        "1" -> {
            TsundokuListScreen(
                selectedCategoryTsundokuList = uiState.tsundoku,
                navigator = navigator,
            )
        }

        "2" -> {
            TsundokuListScreen(
                selectedCategoryTsundokuList = uiState.tsundoku.filter { it.isFavorite },
                navigator = navigator,
            )
        }

        else -> {
            TsundokuListScreen(
                selectedCategoryTsundokuList = uiState.tsundoku
                    .filter { it.categoryId == uiState.selectedCategory.id },
                navigator = navigator,
            )
        }
    }
}
