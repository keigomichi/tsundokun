package com.example.tsundokun.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.repository.CategoryRepository
import com.example.tsundokun.data.repository.TsundokuRepository
import com.example.tsundokun.domain.models.Category
import com.example.tsundokun.domain.models.Tsundoku
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tsundokuRepository: TsundokuRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            categoryRepository.initializeDatabaseWithDefaultData()
        }
        try {
            val categoryState = categoryRepository.observeAll()
            val tsundokuState = tsundokuRepository.observeAll()
            viewModelScope.launch {
                combine(tsundokuState, categoryState) { tsundoku, category ->
                    HomeUiState(tsundoku, category)
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

    fun updateFavorite(id: String) = viewModelScope.launch {
        tsundokuRepository.updateFavorite(id)
    }

    fun onTabSelected(index: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedTab = index)
        }
    }

    data class HomeUiState(
        val tsundoku: List<Tsundoku> = emptyList(),
        val category: List<Category> = emptyList(),
        val selectedTab: Int = 0
    )
}
