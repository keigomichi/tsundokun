package com.example.tsundokun.ui.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.ui.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val tsundokuDao: TsundokuDao,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val navArgs: ConfirmScreenNavArgs = savedStateHandle.navArgs()

    fun addTsundoku() {
        viewModelScope.launch {
            tsundokuDao.upsert(
                TsundokuEntity(
                    id = UUID.randomUUID().toString(),
                    link = navArgs.link,
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
