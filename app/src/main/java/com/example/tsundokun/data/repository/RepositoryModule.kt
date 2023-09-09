package com.example.tsundokun.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        defaultCategoryRepository: DefaultCategoryRepository,
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindTsundokuRepository(
        defaultTsundokuRepository: DefaultTsundokuRepository,
    ): TsundokuRepository
}
