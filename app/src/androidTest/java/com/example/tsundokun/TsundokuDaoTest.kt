package com.example.tsundokun

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.tsundokun.data.local.TsundokuEntity
import com.example.tsundokun.data.local.TsundokunDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class TsundokuDaoTest {
    private lateinit var database: TsundokunDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TsundokunDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Test
    fun insertTsundokuAndGetById() = runTest {
        val tsundoku = TsundokuEntity(
            id = "id",
            link = "https://talent.supporterz.jp",
            isRead = false,
            isFavorite = false,
            createdAt = "2021-08-01 00:00:00",
            updatedAt = "2021-08-01 00:00:00",
            deletedAt = "2021-08-01 00:00:00",
        )
        database.tsundokuDao().upsert(tsundoku)

        val loaded = database.tsundokuDao().getById(tsundoku.id)

        assertNotNull(loaded as TsundokuEntity)
        assertEquals(tsundoku.id, loaded.id)
        assertEquals(tsundoku.link, loaded.link)
        assertEquals(tsundoku.isRead, loaded.isRead)
        assertEquals(tsundoku.isFavorite, loaded.isFavorite)
        assertEquals(tsundoku.createdAt, loaded.createdAt)
        assertEquals(tsundoku.updatedAt, loaded.updatedAt)
        assertEquals(tsundoku.deletedAt, loaded.deletedAt)
    }

    @Test
    fun insertTsundokuReplacesOnConflict() = runTest {
        val tsundoku = TsundokuEntity(
            id = "id",
            link = "https://talent.supporterz.jp",
            isRead = false,
            isFavorite = false,
            createdAt = "2021-08-01 00:00:00",
            updatedAt = "2021-08-01 00:00:00",
            deletedAt = "2021-08-01 00:00:00",
        )
        database.tsundokuDao().upsert(tsundoku)

        val newTsundoku = TsundokuEntity(
            id = tsundoku.id,
            link = "https://biz.supporterz.jp/geekpjt",
            isRead = true,
            isFavorite = true,
            createdAt = "2022-08-01 00:00:00",
            updatedAt = "2022-08-01 00:00:00",
            deletedAt = "2022-08-01 00:00:00",
        )

        database.tsundokuDao().upsert(newTsundoku)

        val loaded = database.tsundokuDao().getById(tsundoku.id)
        assertEquals(tsundoku.id, loaded?.id)
        assertEquals(newTsundoku.link, loaded?.link)
        assertEquals(newTsundoku.isRead, loaded?.isRead)
        assertEquals(newTsundoku.isFavorite, loaded?.isFavorite)
        assertEquals(newTsundoku.createdAt, loaded?.createdAt)
        assertEquals(newTsundoku.updatedAt, loaded?.updatedAt)
        assertEquals(newTsundoku.deletedAt, loaded?.deletedAt)
    }

    @Test
    fun insertTsundokuAndGetTsundokus() = runTest {
        val tsundoku = TsundokuEntity(
            id = "id",
            link = "https://talent.supporterz.jp",
            isRead = false,
            isFavorite = false,
            createdAt = "2021-08-01 00:00:00",
            updatedAt = "2021-08-01 00:00:00",
            deletedAt = "2021-08-01 00:00:00",
        )
        database.tsundokuDao().upsert(tsundoku)

        val tsundokus = database.tsundokuDao().getAll()

        assertEquals(1, tsundokus.size)
        assertEquals(tsundoku.id, tsundokus[0].id)
        assertEquals(tsundoku.link, tsundokus[0].link)
        assertEquals(tsundoku.isRead, tsundokus[0].isRead)
        assertEquals(tsundoku.isFavorite, tsundokus[0].isFavorite)
        assertEquals(tsundoku.createdAt, tsundokus[0].createdAt)
        assertEquals(tsundoku.updatedAt, tsundokus[0].updatedAt)
        assertEquals(tsundoku.deletedAt, tsundokus[0].deletedAt)
    }

    @Test
    fun updateTsundokuAndGetById() = runTest {
        val originalTsundoku = TsundokuEntity(
            id = "id",
            link = "https://talent.supporterz.jp",
            isRead = false,
            isFavorite = false,
            createdAt = "2021-08-01 00:00:00",
            updatedAt = "2021-08-01 00:00:00",
            deletedAt = "2021-08-01 00:00:00",
        )
        database.tsundokuDao().upsert(originalTsundoku)

        val updatedTsundoku = TsundokuEntity(
            id = originalTsundoku.id,
            link = "https://biz.supporterz.jp/geekpjt",
            isRead = true,
            isFavorite = true,
            createdAt = "2022-08-01 00:00:00",
            updatedAt = "2022-08-01 00:00:00",
            deletedAt = "2022-08-01 00:00:00",
        )
        database.tsundokuDao().upsert(updatedTsundoku)

        val loaded = database.tsundokuDao().getById(originalTsundoku.id)
        assertEquals(originalTsundoku.id, loaded?.id)
        assertEquals(updatedTsundoku.link, loaded?.link)
        assertEquals(updatedTsundoku.isRead, loaded?.isRead)
        assertEquals(updatedTsundoku.isFavorite, loaded?.isFavorite)
        assertEquals(updatedTsundoku.createdAt, loaded?.createdAt)
        assertEquals(updatedTsundoku.updatedAt, loaded?.updatedAt)
        assertEquals(updatedTsundoku.deletedAt, loaded?.deletedAt)
    }

    @Test
    fun updateReadAndGetById() = runTest {
        val tsundoku = TsundokuEntity(
            id = "id",
            link = "https://talent.supporterz.jp",
            isRead = false,
            isFavorite = false,
            createdAt = "2021-08-01 00:00:00",
            updatedAt = "2021-08-01 00:00:00",
            deletedAt = "2021-08-01 00:00:00",
        )
        database.tsundokuDao().upsert(tsundoku)

        database.tsundokuDao().updateRead(tsundoku.id, true)

        val loaded = database.tsundokuDao().getById(tsundoku.id)
        assertEquals(tsundoku.id, loaded?.id)
        assertEquals(tsundoku.link, loaded?.link)
        assertEquals(true, loaded?.isRead)
        assertEquals(tsundoku.isFavorite, loaded?.isFavorite)
        assertEquals(tsundoku.createdAt, loaded?.createdAt)
        assertEquals(tsundoku.updatedAt, loaded?.updatedAt)
        assertEquals(tsundoku.deletedAt, loaded?.deletedAt)
    }

    @Test
    fun deleteTsundokuByIdAndGettingTsundokus() = runTest {
        val tsundoku = TsundokuEntity(
            id = "id",
            link = "https://talent.supporterz.jp",
            isRead = false,
            isFavorite = false,
            createdAt = "2021-08-01 00:00:00",
            updatedAt = "2021-08-01 00:00:00",
            deletedAt = "2021-08-01 00:00:00",
        )
        database.tsundokuDao().upsert(tsundoku)

        database.tsundokuDao().deleteById(tsundoku.id)

        val tsundokus = database.tsundokuDao().getAll()
        assertEquals(true, tsundokus.isEmpty())
    }

    @Test
    fun deleteTsundokusAndGettingTsundokus() = runTest {
        val tsundoku = TsundokuEntity(
            id = "id",
            link = "https://talent.supporterz.jp",
            isRead = false,
            isFavorite = false,
            createdAt = "2021-08-01 00:00:00",
            updatedAt = "2021-08-01 00:00:00",
            deletedAt = "2021-08-01 00:00:00",
        )
        database.tsundokuDao().upsert(tsundoku)

        database.tsundokuDao().deleteAll()

        val tsundokus = database.tsundokuDao().getAll()
        assertEquals(true, tsundokus.isEmpty())
    }
}
