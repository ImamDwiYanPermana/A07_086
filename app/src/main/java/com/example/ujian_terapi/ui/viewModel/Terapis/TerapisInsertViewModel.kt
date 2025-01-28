package com.example.ujian_terapi.ui.viewModel.Terapis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.repository.terapisRepository
import com.example.ujian_terapi.ui.componen.toTerapis
import kotlinx.coroutines.launch

class TerapisInsertViewModel(
    private val terapisRepository: terapisRepository
) : ViewModel() {
    var uiState by mutableStateOf(TerapisUiEvent(
        namaTerapis = TODO(),
        spesialisasi = TODO(),
        nomorIzinPraktik = TODO(),
        idTerapis = TODO()
    ))
        private set

    fun updateTerapisState(event: TerapisUiEvent) {
        uiState = event
    }

    suspend fun insertTerapis() {
        viewModelScope.launch {
            try {
                terapisRepository.insertTerapis(uiState.toTerapis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}