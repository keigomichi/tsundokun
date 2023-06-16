package com.example.tsundokun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "tsundoku_category",
    primaryKeys = ["tsundoku_id", "category_id"],
    foreignKeys = [
        ForeignKey(
            entity = TsundokuEntity::class,
            parentColumns = ["id"],
            childColumns = ["tsundoku_id"],
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
        ),
    ],
    indices = [
        Index(value = ["tsundoku_id"]),
        Index(value = ["category_id"]),
    ],
)
data class TsundokuCategoryEntity(
    @ColumnInfo(name = "tsundoku_id")
    val tsundokuId: String,
    @ColumnInfo(name = "category_id")
    val categoryId: String,
)
