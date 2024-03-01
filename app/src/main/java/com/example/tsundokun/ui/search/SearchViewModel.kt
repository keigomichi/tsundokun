package com.example.tsundokun.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.core.model.Tsundoku
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.repository.TsundokuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    tsundokuRepository: TsundokuRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val tsundokuList = tsundokuRepository.observeAll()

    fun search(query: String) {
        viewModelScope.launch {
            try {
                tsundokuList.map { tsundokuList ->
                    tsundokuList.filter { tsundoku ->
                        tsundoku.title?.contains(query, ignoreCase = true) == true
                    }
                }.collect { filteredList ->
                    _uiState.value = _uiState.value.copy(searchResults = filteredList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    data class SearchUiState(
        var searchQuery: String = "",
        var searchResults: List<Tsundoku> = emptyList(),
    )
}