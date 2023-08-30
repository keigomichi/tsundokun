package com.example.tsundokun.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.local.entities.CategoryEntity
import com.example.tsundokun.data.repository.CategoryRepository
import com.example.tsundokun.data.repository.TsundokuRepository
import com.example.tsundokun.domain.models.Category
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

    getTsundokuUseCase: GetTsundokuUseCase,
    private val tsundokuRepository: TsundokuRepository,
    private val categoryRepository: CategoryRepository

) : ViewModel() {
    private val _uiState = MutableStateFlow(TsundokuUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tsundokuRepository.initializeDatabaseWithDefaultData()
        }
        try {
            val categoryState = getTsundokuUseCase.observeAllCategory
            val tsundokuState = getTsundokuUseCase.observeAllTsundoku
            viewModelScope.launch {
                combine(tsundokuState, categoryState) { tsundoku, category ->
                    TsundokuUiState(tsundoku, category)
                }.collect { _uiState.value = it }
            }
        } catch (_: Exception) {
        }
    }

    fun deleteTsundoku(tsundoku: Tsundoku) {
        viewModelScope.launch {
            tsundokuRepository.deleteTsundokuById(tsundoku.id)
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.addCategory(category)
        }
    }

    fun updateFavorite(id: String, isFavorite: Boolean) = viewModelScope.launch {
        tsundokuRepository.updateFavorite(id, isFavorite)
    }

    data class TsundokuUiState(
        val tsundoku: List<Tsundoku> = emptyList(),
        val category: List<CategoryEntity> = emptyList()
    )
}
