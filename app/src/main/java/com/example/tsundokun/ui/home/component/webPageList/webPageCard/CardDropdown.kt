package com.example.tsundokun.ui.home.component.webPageList.webPageCard

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tsundokun.core.model.Tsundoku
import com.example.tsundokun.ui.home.HomeViewModel

@Composable
fun CardDropdown(expandedState: MutableState<Boolean>, viewModel: HomeViewModel, tsundoku: Tsundoku) {
    DropdownMenu(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            // タップされた時の背景を円にする
            .clip(RoundedCornerShape(16.dp)),
        expanded = expandedState.value,
        // メニューの外がタップされた時に閉じる
        onDismissRequest = { expandedState.value = false },
    ) {
        DropdownMenuItem(
            text = { Text(text = "削除") },
            onClick = {
                viewModel.deleteTsundoku(tsundoku)
                expandedState.value = false
            },
        )
    }
}
