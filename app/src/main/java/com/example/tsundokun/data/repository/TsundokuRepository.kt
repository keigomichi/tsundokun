package com.example.tsundokun.data.repository

import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.domain.models.Tsundoku
import kotlinx.coroutines.flow.Flow

interface TsundokuRepository {
    fun observeAllTsundoku(): Flow<List<Tsundoku>>
    suspend fun deleteTsundokuById(id: String)
    suspend fun addTsundoku(link: String, categoryId: String)
    suspend fun updateFavorite(id: String)
}
