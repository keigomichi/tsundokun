package com.example.tsundokun.data.repository

import com.example.tsundokun.data.local.dao.TsundokuCategoryDao
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.TsundokuCategoryEntity
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.data.local.entities.toTsundoku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTsundokuRepository @Inject constructor(
    private val tsundokuDao: TsundokuDao,
    private val tsundokuCategoryDao: TsundokuCategoryDao
) : TsundokuRepository {

    override fun observeAll() = tsundokuDao.observeAll().map {
        it.toTsundoku()
    }

    override suspend fun deleteTsundokuById(id: String) = tsundokuDao.deleteById(id)

    override suspend fun addTsundoku(link: String, categoryId: String) {
        withContext(Dispatchers.IO) {
            val tsundokuEntity = TsundokuEntity.fromLink(link)
            //最初に、tsundokuテーブルに追加
            tsundokuDao.upsert(tsundokuEntity)
            //次に、tsundoku_categoryテーブルに追加する。supabaseとか、firebaseとかのように、
            //外部キー制約を設定できないので、ここで制約を設ける。
            //usecaseにしないのは、単に、外部キー制約を設けるためだけの処理のため。
            tsundokuCategoryDao.upsert(
                TsundokuCategoryEntity(
                    tsundokuId = tsundokuEntity.id,
                    categoryId = categoryId
                )
            )
        }
    }
//    = tsundokuDao.upsert(tsundoku)

    override suspend fun updateFavorite(id: String) =
        tsundokuDao.updateFavorite(id)
}
