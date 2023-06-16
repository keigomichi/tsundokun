package com.example.tsundokun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "created_at") val created_at: Long,
)
