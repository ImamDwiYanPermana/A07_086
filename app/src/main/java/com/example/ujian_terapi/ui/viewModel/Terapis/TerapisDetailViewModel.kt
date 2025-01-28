package com.example.ujian_terapi.ui.viewModel.Terapis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.repository.terapisRepository
import kotlinx.coroutines.launch

class TerapisDetailViewModel(
    private val terapisRepository: terapisRepository,
    private val idTerapis: Int
) : ViewModel() {
    var detailUiState: TerapisUiState by mutableStateOf(TerapisUiState.Loading)
        private set

    init {
        getTerapisById()
    }

    private fun getTerapisById() {
        viewModelScope.launch {
            detailUiState = try {
                val terapis = terapisRepository.getTerapisById(idTerapis)
                TerapisUiState.Success(listOf(terapis))
            } catch (e: Exception) {
                TerapisUiState.Error
            }
        }
    }
}

data class TerapisUiEvent(
    val namaTerapis: String = "",
    val spesialisasi: String = "",
    val nomorIzinPraktik: String = "",
    val idTerapis: Int
) {

}