package com.example.tsundokun.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.example.tsundokun.ui.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun SettingScreen(navigator: DestinationsNavigator)  {
    Column(){
        TopSettingBar(navigator)
        SettingItem("アカウント設定", "アカウント情報を設定します")
        SettingItem("通知設定", "通知のオン・オフを設定します")
        SettingItem("ログアウト", "")
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
fun TopSettingBar(navigator: DestinationsNavigator) {
    CenterAlignedTopAppBar(
        title = { Text(text = "設定") },
        navigationIcon = {
            IconButton(onClick = { navigator.navigate(HomeScreenDestination())}) {
                Icon(Icons.Filled.Close, contentDescription = "return home")
            }
        }
    )
}
