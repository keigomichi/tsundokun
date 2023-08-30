package com.example.tsundokun.domain.usecases

import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import com.example.tsundokun.data.local.entities.CategoryEntity
import com.example.tsundokun.data.repository.TsundokuRepository
import javax.inject.Inject

class GetTsundokuUseCase @Inject constructor(
    tsundokuRepository: TsundokuRepository
) {
    val observeAllTsundoku = tsundokuRepository.observeAllTsundoku()
    val observeAllCategory = tsundokuRepository.observeAllCategory()

    operator fun invoke(){

    }
}