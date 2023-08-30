package com.example.tsundokun.data.repository

import android.util.Log
import com.example.tsundokun.data.local.dao.CategoryDao
import com.example.tsundokun.data.local.dao.TsundokuCategoryDao
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.CategoryEntity
import com.example.tsundokun.data.local.entities.TsundokuCategoryEntity
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.data.local.entities.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TsundokuRepository @Inject constructor(
    private val tsundokuDao: TsundokuDao,
    private val categoryDao: CategoryDao,
    private val tsundokuCategoryDao: TsundokuCategoryDao
) {

    fun observeAllTsundoku() = tsundokuDao.observeAll().map {
        it.toDomainModel()
    }

    fun observeAllCategory() = categoryDao.observeAll()

    suspend fun deleteTsundokuById(id: String) = tsundokuDao.deleteById(id)

    suspend fun addCategory(category: CategoryEntity) = categoryDao.upsert(category)

    suspend fun addTsundoku(tsundoku: TsundokuEntity, uuid: String, categoryId: String) {
        withContext(Dispatchers.IO) {
            //最初に、tsundokuテーブルに追加
            tsundokuDao.upsert(tsundoku)
            //次に、tsundoku_categoryテーブルに追加する。supabaseとか、firebaseとかのように、
            //外部キー制約を設定できないので、ここで制約を設ける。
            //usecaseにしないのは、単に、外部キー制約を設けるためだけの処理のため。
            tsundokuCategoryDao.upsert(
                TsundokuCategoryEntity(
                    tsundokuId = uuid,
                    categoryId = categoryId
                )
            )
        }
    }
//    = tsundokuDao.upsert(tsundoku)

    suspend fun initializeDatabaseWithDefaultData() {
        val count = categoryDao.getCount()
        Log.d("TsundokuRepository", "initializeDatabaseWithDefaultData: $count")
        if (count == 0) {
            categoryDao.upsert(CategoryEntity("1", "すべて", 1))
            categoryDao.upsert(CategoryEntity("2", "お気に入り", 2))
        }
        Log.d("TsundokuRepository", "initializeDatabaseWithDefaultData: ${categoryDao.getCount()}")
    }

    suspend fun updateFavorite(id: String, isFavorite: Boolean) =
        tsundokuDao.updateFavorite(id, isFavorite)
}



