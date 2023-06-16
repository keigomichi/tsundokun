package com.example.tsundokun.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tsundokun.data.local.category.CategoryDao
import com.example.tsundokun.data.local.category.CategoryEntity
import com.example.tsundokun.data.local.tsundokucategory.TsundokuCategoryEntity

@Database(entities = [TsundokuEntity::class, CategoryEntity::class,TsundokuCategoryEntity::class], version = 1, exportSchema = false)
abstract class TsundokunDatabase : RoomDatabase() {
    abstract fun tsundokuDao(): TsundokuDao
    abstract fun categoryDao(): CategoryDao
}
