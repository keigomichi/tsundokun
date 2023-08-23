package com.example.tsundokun.ui.home.component.tsundokunReport

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import com.example.tsundokun.ui.home.TsundokuUiState
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/*
* 今週の積読した数の算出
*/
@RequiresApi(VERSION_CODES.O)
fun RecentTsundokuData(tsundokuUiState: TsundokuUiState): Int {
    val now = LocalDateTime.now()
    val oneWeekAgo = now.minus(1, ChronoUnit.WEEKS)
    val recentTsundokuData = tsundokuUiState.tsundoku.filter { tsundoku ->
        val date = LocalDateTime.parse(tsundoku.createdAt)
        date.isAfter(oneWeekAgo) && date.isBefore(now)
    }
    return recentTsundokuData.size
}
