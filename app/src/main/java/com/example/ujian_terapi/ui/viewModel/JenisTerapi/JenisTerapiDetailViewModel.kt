package com.example.ujian_terapi.ui.viewModel.JenisTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.repository.jenisTerapiRepository
import kotlinx.coroutines.launch

class JenisTerapiDetailViewModel(
    private val jenisTerapiRepository: jenisTerapiRepository,
    private val idJenis: Int
) : ViewModel() {
    var detailUiState: JenisTerapiUiState by mutableStateOf(JenisTerapiUiState.Loading)
        private set

    init {
        getJenisTerapiById()
    }

    private fun getJenisTerapiById() {
        viewModelScope.launch {
            detailUiState = try {
                val jenisTerapi = jenisTerapiRepository.getJenisTerapiById(idJenis)
                JenisTerapiUiState.Success(listOf(jenisTerapi))
            } catch (e: Exception) {
                JenisTerapiUiState.Error
            }
        }
    }
}

data class JenisTerapiUiEvent(
    val namaJenisTerapi: String = "",
    val deskripsiTerapi: String = "",
    val idJenisTerapi: Int = 0
) {

}