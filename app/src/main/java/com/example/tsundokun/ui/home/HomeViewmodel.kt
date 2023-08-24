package com.example.tsundokun.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.local.entities.CategoryEntity
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.domain.models.Tsundoku
import com.example.tsundokun.domain.usecases.GetTsundokuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val tsundokuUsecase: GetTsundokuUseCase

) : ViewModel() {
    private val _uiState = MutableStateFlow(TsundokuUiState())
    val uiState = _uiState.asStateFlow()
    init {
        try {
            val categoryState = tsundokuUsecase.observeAllCategory
            val tsundokuState = tsundokuUsecase.observeAllTsundoku
            viewModelScope.launch {
                combine(tsundokuState, categoryState) { tsundoku, category ->
                    TsundokuUiState(tsundoku, category)
                }.collect { _uiState.value = it }
            }
        } catch (_: Exception) {
        }
    }

data class TsundokuUiState(
    val tsundoku: List<Tsundoku> = emptyList(),
    val category: List<CategoryEntity> = emptyList()
)
}
