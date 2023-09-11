package com.example.tsundokun.data.repository

import com.example.tsundokun.data.local.dao.CategoryDao
import com.example.tsundokun.data.local.entities.CategoryEntity
import com.example.tsundokun.data.local.entities.toCategory
import com.example.tsundokun.domain.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    override fun observeAll(): Flow<List<Category>> = categoryDao.observeAll()
        .map {
            it.toCategory()
        }

    override suspend fun addCategory(category: Category) =
        withContext(Dispatchers.IO) {
            val categoryEntity = CategoryEntity.fromCategory(category)
            categoryDao.upsert(categoryEntity)
        }

    override suspend fun initializeDatabaseWithDefaultData() {
        val count = categoryDao.getCount()
        if (count == 0) {
            categoryDao.upsert(CategoryEntity("1", "すべて", 1))
            categoryDao.upsert(CategoryEntity("2", "お気に入り", 2))
        }
    }
}
