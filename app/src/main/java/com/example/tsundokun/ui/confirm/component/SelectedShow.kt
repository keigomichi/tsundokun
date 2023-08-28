package com.example.tsundokun.ui.confirm.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.tsundokun.ui.confirm.SelectedField

/*
 * アイコンとカテゴリ選択
 */
@Composable
fun SelectedShow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    title: String = "",
    text: String,
    onTextChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(end = 8.dp),
        )
        SelectedField(
            modifier = Modifier.weight(1f),
            title = title,
            text = text,
            onTextChange = onTextChange,
        )
    }
}
