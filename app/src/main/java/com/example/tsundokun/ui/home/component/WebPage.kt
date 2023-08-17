package com.example.tsundokun.ui.home.component

/*
 * Webページの情報のデータクラス
 */
data class WebPage(
    val title: String?,
    val ogpImageUrl: String?,
    val faviconImageUrl: String?,
    val link: String?,
)
/*
 * タブの種類
 */
enum class Screen {
    ALL, FAVORITE
}


