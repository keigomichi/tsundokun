package com.example.tsundokun.ui.home.component

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tsundokun.R.string
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/*
 * ホーム画面のアプリバー
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navigator: DestinationsNavigator) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(id = string.app_name)) },
        navigationIcon = {
            IconButton(onClick = { /* 検索する */ }) {
                Icon(
                    imageVector = Filled.Search,
                    contentDescription = stringResource(string.button_search_description),
                )
            }
        },
        actions = {
            Dropdown(navigator)
        },
    )
}