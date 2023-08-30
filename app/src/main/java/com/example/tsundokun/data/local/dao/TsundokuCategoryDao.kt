package com.example.tsundokun.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.tsundokun.data.local.entities.TsundokuCategoryEntity

@Dao
interface TsundokuCategoryDao {
    @Upsert
    suspend fun upsert(tsundokuCategory: TsundokuCategoryEntity)
}
