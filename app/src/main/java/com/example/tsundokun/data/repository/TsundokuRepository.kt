package com.example.tsundokun.data.repository

import com.example.tsundokun.domain.models.Tsundoku
import kotlinx.coroutines.flow.Flow

interface TsundokuRepository {
    fun observeAllTsundoku(): Flow<List<Tsundoku>>
    suspend fun deleteTsundokuById(id: String)
    suspend fun addTsundoku(tsundoku: Tsundoku)
    suspend fun updateFavorite(id: String)
}
