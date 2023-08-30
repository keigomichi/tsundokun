package com.example.tsundokun.data.repository

import com.example.tsundokun.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeAllCategory(): Flow<List<Category>>
    suspend fun addCategory(category: Category)
    suspend fun initializeDatabaseWithDefaultData()
}
