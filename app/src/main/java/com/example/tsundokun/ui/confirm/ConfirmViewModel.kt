package com.example.tsundokun.ui.confirm

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.repository.CategoryRepository
import com.example.tsundokun.data.repository.TsundokuRepository
import com.example.tsundokun.domain.models.Category
import com.example.tsundokun.ui.confirm.component.data.ConfirmScreenNavArgs
import com.example.tsundokun.ui.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val tsundokuRepository: TsundokuRepository,
    categoryRepository: CategoryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val navArgs: ConfirmScreenNavArgs = savedStateHandle.navArgs()

    //    private val _uiState = MutableStateFlow(ConfirmUiState())
    val uiState: StateFlow<ConfirmUiState> = categoryRepository.observeAll().map {
        ConfirmUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConfirmUiState()
    )

//    init {
//        try {
//            val categoryState = categoryRepository.observeAll()
//            viewModelScope.launch {
//                categoryState.collect { _uiState.value = _uiState.value.copy(categories = it) }
//            }
//        } catch (_: Exception) {
//        }
//    }

    fun addTsundoku() {
        Log.d("ConfirmViewModel", "addTsundoku: ${navArgs.categoryId}")
        viewModelScope.launch {
            tsundokuRepository.addTsundoku(
                link = navArgs.link,
                categoryId = navArgs.categoryId
            )
        }
    }

    data class ConfirmUiState(
        val categories: List<Category> = emptyList()
    )
}
