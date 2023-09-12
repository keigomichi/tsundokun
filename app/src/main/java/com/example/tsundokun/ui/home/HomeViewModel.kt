package com.example.tsundokun.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.core.model.Category
import com.example.tsundokun.core.model.Tsundoku
import com.example.tsundokun.data.repository.CategoryRepository
import com.example.tsundokun.data.repository.TsundokuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tsundokuRepository: TsundokuRepository,
    private val categoryRepository: CategoryRepository,
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
                    Log.d("HomeViewModel", "HomeViewModel: $category")
                    HomeUiState(tsundoku, category) // 0を変えるとタブの初期位置が変わる
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

    fun onAddTabClick(category: String) {
        viewModelScope.launch {
            categoryRepository.addCategory(Category(label = category))
        }
    }

    fun updateFavorite(id: String) = viewModelScope.launch {
        tsundokuRepository.updateFavorite(id)
    }

    fun onTabSelected(index: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedCategory = _uiState.value.category[index])
        }
    }

    data class HomeUiState(
        val tsundoku: List<Tsundoku> = emptyList(),
        val category: List<Category> = emptyList(),
        val selectedCategory: Category = Category(id = "1"), // アプリ初起動時にupsertされるカテゴリ「すべて」のcategoryId。これをDataStoreに保存すると、アプリ起動時に前回のカテゴリが選択されるようになる。
    )
}
