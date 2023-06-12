package com.example.tsundokun.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TsundokuEntity::class], version = 1)
abstract class TsundokunDatabase : RoomDatabase() {
    abstract fun tsundokuDao(): TsundokuDao
}
