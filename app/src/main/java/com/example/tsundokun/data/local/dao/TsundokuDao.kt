package com.example.tsundokun.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tsundokun.data.local.entities.TsundokuEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface TsundokuDao {
    @Query("SELECT * FROM tsundoku")
    fun observeAll(): Flow<List<TsundokuEntity>>

    @Query("SELECT * FROM tsundoku WHERE id = :id")
    fun observeById(id: String): Flow<TsundokuEntity>

    // カテゴリーから積読の取得(リアルタイム)
//    @Query("SELECT tsundoku.id, tsundoku.link, tsundoku.is_read, tsundoku.is_favorite, tsundoku.created_at, tsundoku.update_at, tsundoku.deleted_at FROM tsundoku INNER JOIN tsundoku_category ON tsundoku.id = tsundoku_category.tsundoku_id WHERE tsundoku_category.category_id = :category_id")
//    fun observeTsundokuByCategoryId(category_id: String): Flow<List<TsundokuEntity>>

    // カテゴリーから積読の取得
//    @Query("SELECT tsundoku.id, tsundoku.link, tsundoku.is_read, tsundoku.is_favorite, tsundoku.created_at, tsundoku.update_at, tsundoku.deleted_at FROM tsundoku INNER JOIN tsundoku_category ON tsundoku.id = tsundoku_category.tsundoku_id WHERE tsundoku_category.category_id = :category_id")
//    fun getTsundokuByCategoryId(category_id: String): List<TsundokuEntity>

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
