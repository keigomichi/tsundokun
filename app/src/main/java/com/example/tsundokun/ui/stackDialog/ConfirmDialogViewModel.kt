package com.example.tsundokun.ui.stackDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.TsundokuEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConfirmDialogViewModel @Inject constructor(
    private val tsundokuDao: TsundokuDao,
) : ViewModel() {

    fun addTsundoku(linkText: String) {
        viewModelScope.launch {
            tsundokuDao.upsert(
                TsundokuEntity(
                    id = UUID.randomUUID().toString(),
                    link = linkText,
                    isRead = false,
                    isFavorite = false,
                    createdAt = "",
                    updatedAt = "",
                    deletedAt = "",
                ),
            )
        }
    }
}