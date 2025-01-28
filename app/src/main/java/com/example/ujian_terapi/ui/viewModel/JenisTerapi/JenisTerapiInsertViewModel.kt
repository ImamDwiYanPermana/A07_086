package com.example.ujian_terapi.ui.viewModel.JenisTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.repository.jenisTerapiRepository
import com.example.ujian_terapi.ui.componen.toJenisTerapi
import kotlinx.coroutines.launch

class JenisTerapiInsertViewModel(
    private val jenisTerapiRepository: jenisTerapiRepository
) : ViewModel() {
    var uiState by mutableStateOf(JenisTerapiUiEvent())
        private set

    fun updateJenisTerapiState(event: JenisTerapiUiEvent) {
        uiState = event
    }

    suspend fun insertJenisTerapi() {
        viewModelScope.launch {
            try {
                jenisTerapiRepository.insertJenisTerapi(uiState.toJenisTerapi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
