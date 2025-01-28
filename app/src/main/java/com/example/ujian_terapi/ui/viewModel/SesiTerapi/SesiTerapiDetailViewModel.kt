package com.example.ujian_terapi.ui.viewModel.SesiTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.repository.sesiTerapiRepository
import kotlinx.coroutines.launch

class SesiTerapiDetailViewModel(
    private val sesiTerapiRepository: sesiTerapiRepository,
    private val idSesi: Int
) : ViewModel() {
    var detailUiState: SesiTerapiUiState by mutableStateOf(SesiTerapiUiState.Loading)
        private set

    init {
        getSesiTerapiById()
    }

    private fun getSesiTerapiById() {
        viewModelScope.launch {
            detailUiState = try {
                val sesiTerapi = sesiTerapiRepository.getSesiTerapiById(idSesi)
                SesiTerapiUiState.Success(listOf(sesiTerapi))
            } catch (e: Exception) {
                SesiTerapiUiState.Error
            }
        }
    }
}

data class SesiTerapiUiEvent(
    val idPasien: Int = 0,
    val idTerapis: Int = 0,
    val idJenisTerapi: Int = 0,
    val idSesi: Int =0,
    val tanggalSesi: String = "",
    val catatanSesi: String = ""
) {

}