package com.example.tsundokun.data.repository

import com.example.tsundokun.core.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeAll(): Flow<List<Category>>
    suspend fun addCategory(category: Category)
    suspend fun initializeDatabaseWithDefaultData()
}
