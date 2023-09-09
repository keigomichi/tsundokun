package com.example.tsundokun.ui.confirm.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/*
 * タイトル表示
 */
@Composable
fun ShowText(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    title: String = "",
) {
    Row(
        modifier = modifier.padding(0.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(end = 8.dp),
        )
        Text(
            text = title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )
    }
}
