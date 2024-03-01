package com.example.tsundokun.ui.search

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tsundokun.R.string
import com.example.tsundokun.ui.home.component.webPageList.TsundokuListScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(VERSION_CODES.O)
@RootNavGraph(start = false)
@Destination
@Composable
fun SearchScreen(navigator: DestinationsNavigator) {
    val viewModel: SearchViewModel = hiltViewModel()
    val searchUiState by viewModel.uiState.collectAsState()

    var searchQuery by rememberSaveable { mutableStateOf(searchUiState.searchQuery) }
    var active by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            SearchBar(
                query = searchQuery,
                onQueryChange = { newQuery -> searchQuery = newQuery },
                onSearch = {
                    if (searchQuery.isNotEmpty()) {
                        viewModel.search(searchQuery)
                    }
                },
                active = active,
                onActiveChange = {
                    active = it
                },
                content = {
                    TsundokuListScreen(selectedCategoryTsundokuList = searchUiState.searchResults, navigator = navigator)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(string.button_close),
                        modifier = Modifier.clickable { navigator.popBackStack() },
                    )
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable { searchQuery = "" },
                        )
                    }
                },
                placeholder = { Text(stringResource(id = string.search)) },
            )
        }
    }
}
