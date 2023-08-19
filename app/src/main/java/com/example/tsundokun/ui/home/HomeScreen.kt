package com.example.tsundokun.ui.home

import android.os.Build.VERSION_CODES
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tsundokun.R
import com.example.tsundokun.ui.destinations.StackScreenDestination
import com.example.tsundokun.ui.home.component.TopAppBar
import com.example.tsundokun.ui.home.component.TsundokunReport
import com.example.tsundokun.ui.home.component.WebPageListScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
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
            topBar = { TopAppBar(navigator) },
            floatingActionButton = { AddFab(navigator) },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                TsundokunReport(Modifier, tsundokuUiState.tsundoku.size)
                WebPageListScreen(tsundokuUiState.tsundoku, navigator)
            }
        }
    }
}

/*
 * FAB(追加ボタン)
 */
@Composable
@Destination
fun AddFab(navigator: DestinationsNavigator) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(R.string.fab_add)) },
        icon = { Icon(Filled.Add, contentDescription = stringResource(R.string.fab_add)) },
        onClick = { navigator.navigate(StackScreenDestination()) },
    )
}

@Destination
@Composable
fun OpenWebView(url: String) {
    AndroidView(factory = { WebView(it) }) { webView ->
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
    }
}