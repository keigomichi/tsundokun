package com.example.tsundokun.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tsundokun.data.local.dao.CategoryDao
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.CategoryEntity
import com.example.tsundokun.data.local.entities.TsundokuEntity

@Database(
    entities = [TsundokuEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class TsundokunDatabase : RoomDatabase() {
    abstract fun tsundokuDao(): TsundokuDao
    abstract fun categoryDao(): CategoryDao
}
