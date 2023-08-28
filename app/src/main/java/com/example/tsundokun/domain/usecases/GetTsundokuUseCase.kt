package com.example.tsundokun.domain.usecases

import com.example.tsundokun.data.local.entities.CategoryEntity
import com.example.tsundokun.data.local.repository.TsundokuRepository
import javax.inject.Inject

class GetTsundokuUseCase @Inject constructor(
    tsundokuRepository: TsundokuRepository
) {
    val observeAllTsundoku = tsundokuRepository.observeAllTsundoku()
    val observeAllCategory = tsundokuRepository.observeAllCategory()

    //addCategoryの呼び出し

}