package com.example.tsundokun.data.local.repository

import com.example.tsundokun.data.local.dao.CategoryDao
import com.example.tsundokun.data.local.dao.TsundokuDao
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
    }



