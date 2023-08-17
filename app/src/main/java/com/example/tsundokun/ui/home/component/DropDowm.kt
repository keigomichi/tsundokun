package com.example.tsundokun.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tsundokun.R.string
import com.example.tsundokun.ui.destinations.SettingScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/*
 * アプリバー右上のメニューバーを押したときに表示させる
 * ドロップダウンリスト
 */
@Composable
fun Dropdown(navigator: DestinationsNavigator) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopStart),
    ) {
        IconButton(
            onClick = {
                expanded = true
            },
        ) {
            Icon(
                imageVector = Filled.MoreVert,
                contentDescription = stringResource(string.button_morevert_description),
            )
        }
        DropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                // タップされた時の背景を円にする
                .clip(RoundedCornerShape(16.dp)),
            expanded = expanded,
            // メニューの外がタップされた時に閉じる
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = string.dropdown_menuitem_setting)) },
                onClick = { navigator.navigate(SettingScreenDestination()) },
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = string.dropdown_memuitem_export)) },
                onClick = { /*TODO*/ },
            )
        }
    }
}
