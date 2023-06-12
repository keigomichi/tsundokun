package com.example.tsundokun.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TsundokuDao {
    @Query("SELECT * FROM tsundoku")
    fun observeAll(): Flow<List<TsundokuEntity>>

    @Query("SELECT * FROM tsundoku WHERE id = :id")
    fun observeById(id: String): Flow<TsundokuEntity>

    @Query("SELECT * FROM tsundoku")
    suspend fun getAll(): List<TsundokuEntity>

    @Query("SELECT * FROM tsundoku WHERE id = :id")
    suspend fun getById(id: String): TsundokuEntity?

    @Upsert
    suspend fun upsert(tsundoku: TsundokuEntity)

    @Upsert
    suspend fun upsertAll(tsundokus: List<TsundokuEntity>)

    @Query("UPDATE tsundoku SET is_read = :isRead WHERE id = :id")
    suspend fun updateRead(id: String, isRead: Boolean)

    @Query("UPDATE tsundoku SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("DELETE FROM tsundoku WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM tsundoku")
    suspend fun deleteAll()
}