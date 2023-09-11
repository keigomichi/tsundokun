package com.example.tsundokun.data.repository

import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.data.local.entities.toTsundoku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTsundokuRepository @Inject constructor(
    private val tsundokuDao: TsundokuDao,
) : TsundokuRepository {

    override fun observeAll() = tsundokuDao.observeAll().map {
        it.toTsundoku()
    }

    override suspend fun deleteTsundokuById(id: String) = tsundokuDao.deleteById(id)

    override suspend fun addTsundoku(link: String, categoryId: String) {
        withContext(Dispatchers.IO) {
            val tsundokuEntity = TsundokuEntity.fromLinkAndCategoryId(link, categoryId)
            tsundokuDao.upsert(tsundokuEntity)
        }
    }

    override suspend fun updateFavorite(id: String) =
        tsundokuDao.updateFavorite(id)
}
