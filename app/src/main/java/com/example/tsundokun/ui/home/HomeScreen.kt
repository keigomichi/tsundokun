package com.example.tsundokun.ui.home

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tsundokun.ui.home.component.AddFab
import com.example.tsundokun.ui.home.component.topAppBar.TopAppBar
import com.example.tsundokun.ui.home.component.tsundokunReport.RecentTsundokuData
import com.example.tsundokun.ui.home.component.tsundokunReport.TsundokunReport
import com.example.tsundokun.ui.home.component.webPageList.WebPageListScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(VERSION_CODES.O)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator, viewModel: HomeViewModel = hiltViewModel()) {
    val tsundokuUiState by viewModel.uiState.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = { TopAppBar(navigator = navigator) },
            floatingActionButton = { AddFab(navigator) },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                TsundokunReport(Modifier, RecentTsundokuData(tsundokuUiState))
                WebPageListScreen(tsundokuUiState.tsundoku, navigator)
            }
        }
    }
}
