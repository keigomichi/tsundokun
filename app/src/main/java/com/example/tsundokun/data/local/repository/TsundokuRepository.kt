package com.example.tsundokun.data.local.repository

import android.util.Log
import com.example.tsundokun.data.local.dao.CategoryDao
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.CategoryEntity
import com.example.tsundokun.data.local.entities.toDomainModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TsundokuRepository @Inject constructor(
    private val tsundokuDao: TsundokuDao,
    private val categoryDao: CategoryDao
){

    fun observeAllTsundoku() = tsundokuDao.observeAll().map {
        it.toDomainModel()
        }
    fun observeAllCategory() = categoryDao.observeAll()

    suspend fun deleteTsundokuById(id: String) = tsundokuDao.deleteById(id)

     suspend fun addCategory(category: CategoryEntity) = categoryDao.upsert(category)

    suspend fun initializeDatabaseWithDefaultData(){
        val count = categoryDao.getCount()
        Log.d("TsundokuRepository", "initializeDatabaseWithDefaultData: $count")
        if(count == 0){
            categoryDao.upsert(CategoryEntity("1", "すべて", 1))
            categoryDao.upsert(CategoryEntity("2", "お気に入り", 2))
        }
        Log.d("TsundokuRepository", "initializeDatabaseWithDefaultData: ${categoryDao.getCount()}" )
    }
    }



