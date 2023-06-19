package com.example.tsundokun.data.local

import android.content.Context
import androidx.room.Room
import com.example.tsundokun.data.local.dao.CategoryDao
import com.example.tsundokun.data.local.dao.TsundokuDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTsundokunDatabase(@ApplicationContext context: Context): TsundokunDatabase =
        Room.databaseBuilder(context, TsundokunDatabase::class.java, "tsundoku-database").build()

    @Provides
    fun tsundokuDao(database: TsundokunDatabase): TsundokuDao = database.tsundokuDao()

    @Provides
    fun categoryDao(database: TsundokunDatabase): CategoryDao = database.categoryDao()
}
