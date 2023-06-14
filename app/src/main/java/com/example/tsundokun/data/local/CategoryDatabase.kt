package com.example.tsundokun.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class], version = 1)
abstract class CategoryDatabase : RoomDatabase(){
    abstract fun categoryDao(): CategoryDao
}
