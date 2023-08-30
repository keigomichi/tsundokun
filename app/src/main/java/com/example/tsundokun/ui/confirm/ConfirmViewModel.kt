package com.example.tsundokun.ui.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.data.repository.CategoryRepository
import com.example.tsundokun.data.repository.DefaultTsundokuRepository
import com.example.tsundokun.ui.confirm.component.data.ConfirmScreenNavArgs
import com.example.tsundokun.ui.home.HomeViewModel.TsundokuUiState
import com.example.tsundokun.ui.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val tsundokuRepository: DefaultTsundokuRepository,
    categoryRepository: CategoryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val navArgs: ConfirmScreenNavArgs = savedStateHandle.navArgs()
    private val _uiState = MutableStateFlow(TsundokuUiState())
    val uiState = _uiState.asStateFlow()

    init {
        try {
            val categoryState = categoryRepository.observeAllCategory()
            viewModelScope.launch {
                categoryState.collect { _uiState.value = TsundokuUiState(category = it) }
            }
        } catch (_: Exception) {
        }
    }

    fun addTsundoku() {
        viewModelScope.launch {
            val uuid = UUID.randomUUID().toString()
            tsundokuRepository.addTsundoku(
                TsundokuEntity(
                    id = uuid,
                    link = navArgs.link,
                    isRead = false,
                    isFavorite = false,
                    createdAt = navArgs.createdAt,
                    updatedAt = "",
                    deletedAt = "",
                ),
                uuid,
                navArgs.categoryId
            )
        }
    }
}
