package com.example.tsundokun.ui.confirm

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.core.model.Category
import com.example.tsundokun.data.repository.CategoryRepository
import com.example.tsundokun.data.repository.TsundokuRepository
import com.example.tsundokun.ui.confirm.component.data.ConfirmScreenNavArgs
import com.example.tsundokun.ui.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val tsundokuRepository: TsundokuRepository,
    categoryRepository: CategoryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val navArgs: ConfirmScreenNavArgs = savedStateHandle.navArgs()

    private val _uiState = MutableStateFlow(ConfirmUiState())
    val uiState: StateFlow<ConfirmUiState> = _uiState

    init {
        try {
            val categoryState = categoryRepository.observeAll()
            viewModelScope.launch {
                categoryState.collect { _uiState.value = _uiState.value.copy(categories = it) }
            }
        } catch (_: Exception) {
        }
    }

    fun addTsundoku() {
        Log.d("ConfirmViewModel", "addTsundoku: ${navArgs.categoryId}")
        viewModelScope.launch {
            tsundokuRepository.addTsundoku(
                link = navArgs.link,
                categoryId = uiState.value.selectedCategoryId,
            )
        }
    }

    fun onCategorySelected(categoryId: String) {
        _uiState.value = _uiState.value.copy(selectedCategoryId = categoryId)
    }

    data class ConfirmUiState(
        val categories: List<Category> = emptyList(),
        val selectedCategoryId: String = "",
    )
}
