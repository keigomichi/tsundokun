package com.example.tsundokun.ui.home.component

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tsundokun.R.string
import com.example.tsundokun.ui.destinations.StackScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/*
 * FAB(追加ボタン)
 */
@Composable
@Destination
fun AddFab(navigator: DestinationsNavigator) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(string.fab_add)) },
        icon = { Icon(Filled.Add, contentDescription = stringResource(string.fab_add)) },
        onClick = { navigator.navigate(StackScreenDestination()) },
    )
}
