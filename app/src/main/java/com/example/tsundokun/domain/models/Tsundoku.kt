package com.example.tsundokun.domain.models

data class Tsundoku(
    val id: String,
    val categoryId: String,
    val title: String?,
    val ogpImageUrl: String?,
    val faviconImageUrl: String?,
    val link: String,
    val isFavorite: Boolean,
    val createdAt: String,
    val isRead: Boolean,
    val updatedAt: String,
    val deletedAt: String,
)
