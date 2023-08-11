package com.example.tsundokun.ui.stack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.TsundokuEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StackViewModel @Inject constructor(

    private val tsundokuDao: TsundokuDao,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(TsundokuUiState())
    val uiState = _uiState.asStateFlow()

    init {
        try {
            viewModelScope.launch {
                tsundokuDao.observeAll().collectLatest {
                    _uiState.value = TsundokuUiState(tsundoku = it)
                }
            }
        } catch (_: Exception) {
        }
    }

    fun observeAllTsundoku() =
        viewModelScope.launch {
            tsundokuDao.observeAll()
        }

    fun observeTsundoku(id: String) =
        viewModelScope.launch {
            tsundokuDao.observeById(id)
        }

    fun observeTsundokuByCategoryId(categoryId: String) =
        viewModelScope.launch {
            tsundokuDao.observeTsundokuByCategoryId(categoryId)
        }

    fun upsert(tsundoku: TsundokuEntity) = viewModelScope.launch {
        tsundokuDao.upsert(tsundoku)
    }

    fun upsertAll(tsundokus: List<TsundokuEntity>) = viewModelScope.launch {
        tsundokuDao.upsertAll(tsundokus)
    }
}

data class TsundokuUiState(
    val tsundoku: List<TsundokuEntity> = emptyList(),
)
