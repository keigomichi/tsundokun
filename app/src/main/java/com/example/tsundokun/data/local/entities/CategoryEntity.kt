package com.example.tsundokun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tsundokun.domain.models.Category

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
) {
    fun toCategory() = Category(
        id = id,
        label = label,
        createdAt = createdAt,
    )
}

fun List<CategoryEntity>.toCategory(): List<Category> {
    return map {
        Category(
            id = it.id,
            label = it.label,
            createdAt = it.createdAt,
        )
    }
}
