package com.example.tsundokun.ui.home.component.tsundokunReport

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tsundokun.R.drawable
import com.example.tsundokun.R.string

/*
 * つんどくんのレポートを表示するカード
 * 閉じるボタンを押したら非表示になる
 */
@Composable
fun TsundokunReport(modifier: Modifier = Modifier, recentlySaveCount: Int) {
    val visible = remember { mutableStateOf(true) }
    if (visible.value) {
        Card(
            modifier = modifier
                .padding(16.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(
                        getTsundokunImageTypeByRecentlySaveCount(recentlySaveCount),
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f),
                )
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = string.tsundokun_report, recentlySaveCount),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = modifier.weight(10f),
                        )
                        IconButton(onClick = { visible.value = false }) {
                            Icon(
                                imageVector = Filled.Close,
                                contentDescription = stringResource(string.button_close),
                                Modifier
                                    .width(16.dp)
                                    .weight(1f),
                            )
                        }
                    }
                    Text(
                        text = stringResource(
                            id = getTsundokunReportDetailTypeByRecentlySaveCount(recentlySaveCount),
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = modifier.padding(bottom = 8.dp),
                    )
                }
            }
        }
    }
}

/*
 * 今週積読した記事の数に応じたつんどくんの画像を取得する
 */
fun getTsundokunImageTypeByRecentlySaveCount(recentlySaveCount: Int): Int {
    return if (recentlySaveCount < 5) {
        drawable.low_reading_tsundokun
    } else if (recentlySaveCount < 10) {
        drawable.tsundokun
    } else {
        drawable.high_reading_tsundokun
    }
}

/*
 * 今週積読した記事の数に応じたつんどくんレポート内のコメントを取得する
 */
fun getTsundokunReportDetailTypeByRecentlySaveCount(recentlySaveCount: Int): Int {
    return if (recentlySaveCount < 5) {
        string.low_reading_tsundokun_report_detail
    } else if (recentlySaveCount < 10) {
        string.medium_reading_tsundokun_report_detail
    } else {
        string.high_reading_tsundokun_report_detail
    }
}