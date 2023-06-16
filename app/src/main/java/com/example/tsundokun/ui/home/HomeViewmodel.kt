package com.example.tsundokun.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.TsundokuEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val tsundokuDao : TsundokuDao

): ViewModel(){

    val allTsundokus =
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

    fun updateRead(id: String, isRead: Boolean) = viewModelScope.launch {
        tsundokuDao.updateRead(id, isRead)
    }

    fun updateFavorite(id: String, isFavorite: Boolean) = viewModelScope.launch {
        tsundokuDao.updateFavorite(id, isFavorite)
    }

    fun deleteById(id: String) = viewModelScope.launch {
        tsundokuDao.deleteById(id)
    }

    fun deleteAll() = viewModelScope.launch {
        tsundokuDao.deleteAll()
    }}