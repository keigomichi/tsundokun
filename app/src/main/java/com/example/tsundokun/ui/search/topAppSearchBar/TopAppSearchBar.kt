package com.example.tsundokun.ui.search.topAppSearchBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
 * 検索画面のアプリバー
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppSearchBar(navigator: DestinationsNavigator) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(id = string.search)) },
        navigationIcon = {
            IconButton(onClick = { navigator.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(string.button_close),
                )
            }
        },
    )
}