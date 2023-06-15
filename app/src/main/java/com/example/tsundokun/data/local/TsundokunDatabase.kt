package com.example.tsundokun.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tsundokun.data.local.Category.CategoryDao
import java.util.Locale.Category

@Database(entities = [TsundokuEntity::class], version = 1)
abstract class TsundokunDatabase : RoomDatabase() {
    abstract fun tsundokuDao(): TsundokuDao
    abstract fun categoryDao(): CategoryDao
}
