package com.example.tsundokun.data.local.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "label") val label : String,
    @ColumnInfo(name = "created_at") val created_at:Date,
)
