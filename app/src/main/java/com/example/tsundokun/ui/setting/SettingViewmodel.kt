package com.example.tsundokun.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.TsundokuEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

    private val tsundokuDao: TsundokuDao,

) : ViewModel() {

    private val _uiState = MutableStateFlow(TsundokuUiState())
    val uiState = _uiState.asStateFlow()

    fun deleteAll() = viewModelScope.launch {
        tsundokuDao.deleteAll()
    } }

data class TsundokuUiState(
    val tsundoku: List<TsundokuEntity> = emptyList(),
)
