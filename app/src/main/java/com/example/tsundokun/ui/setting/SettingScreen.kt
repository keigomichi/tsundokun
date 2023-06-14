package com.example.tsundokun.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingScreen(setOpenSettingScreen: () -> Unit)  {
    Column(){
        TopSettingBar(setOpenSettingScreen)
        SettingItem("アカウント設定", "アカウント情報を設定します")
        SettingItem("通知設定", "通知のオン・オフを設定します")
        SettingItem("プライバシー設定", "プライバシー関連の設定を管理します")
        SettingItem("言語設定", "表示言語を選択します")
    }
}

@Composable
fun SettingItem(title: String, description: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 20.dp)
            .clickable { /*TODO*/ }
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        Text(text = description, style = MaterialTheme.typography.labelSmall)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSettingBar(setOpenSettingScreen: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = "設定") },
        navigationIcon = {
            IconButton(onClick = { setOpenSettingScreen }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "return home")
            }
        }
    )
}
