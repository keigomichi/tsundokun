package com.example.tsundokun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tsundokun.core.model.Category
import java.util.UUID

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "label") val label: String = "",
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
) {
    fun toCategory() = Category(
        id = id,
        label = label,
        createdAt = createdAt,
    )

    companion object {
        fun fromCategory(category: Category) = CategoryEntity(
            // 他のプロパティはデフォルト値を設定しているので、ラベルだけを設定する
            label = category.label,
        )
    }
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
