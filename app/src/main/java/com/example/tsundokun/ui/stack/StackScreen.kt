package com.example.tsundokun.ui.stack

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun StackScreen(url: Uri) {
    Column() {
        Text(text = "積読を追加")
        Text(text = url.toString())
    }
}
