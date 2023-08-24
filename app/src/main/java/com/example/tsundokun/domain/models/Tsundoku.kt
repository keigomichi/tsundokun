package com.example.tsundokun.domain.models

data class Tsundoku(
    val id: String,
    val title: String?,
    val ogpImageUrl: String?,
    val faviconImageUrl: String?,
    val link: String?,
    val isFavorite: Boolean,
    val createdAt: String?,
)