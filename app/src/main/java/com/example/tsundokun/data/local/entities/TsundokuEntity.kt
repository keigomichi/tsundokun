package com.example.tsundokun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tsundoku")
data class TsundokuEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "is_read") val isRead: Boolean,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "created_at") var createdAt: String,
    @ColumnInfo(name = "update_at") val updatedAt: String,
    @ColumnInfo(name = "deleted_at") val deletedAt: String,
)
