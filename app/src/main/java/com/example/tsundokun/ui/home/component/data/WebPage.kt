package com.example.tsundokun.ui.home.component.data

/*
 * Webページの情報のデータクラス
 */
data class WebPage(
    val title: String?,
    val ogpImageUrl: String?,
    val faviconImageUrl: String?,
    val link: String?,
    val isFavorite: Boolean,
    val id: String,
)
