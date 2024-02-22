package com.example.tsundokun.ui.search

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tsundokun.ui.search.topAppSearchBar.TopAppSearchBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(VERSION_CODES.O)
@RootNavGraph(start = false)
@Destination
@Composable
fun SearchScreen(navigator: DestinationsNavigator) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = { TopAppSearchBar(navigator = navigator) },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text("search")
            }
        }
    }
}