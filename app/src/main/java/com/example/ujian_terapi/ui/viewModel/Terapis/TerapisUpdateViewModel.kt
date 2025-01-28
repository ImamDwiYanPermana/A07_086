package com.example.ujian_terapi.ui.viewModel.Terapis

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.Terapis
import com.example.ujian_terapi.data.repository.terapisRepository
import kotlinx.coroutines.launch

class UpdateTerapisViewModel(
    savedStateHandle: SavedStateHandle,
    private val terapisRepository: terapisRepository
) : ViewModel() {

    val idTerapis: Int = checkNotNull(savedStateHandle[DestinasiUpdateTerapis.idTerapis])

    var uiState = mutableStateOf(InsertTerapisUiState())

    init {
        getTerapis()
    }

    private fun getTerapis() {
        viewModelScope.launch {
            try {
                val terapis = terapisRepository.getTerapisById(idTerapis.toString())
                terapis?.let {
                    uiState.value = it.toInsertUIEvent()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTerapis(idTerapis: Int, terapis: Terapis) {
        viewModelScope.launch {
            try {
                terapisRepository.updateTerapis(idTerapis.toString(), terapis)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTerapisState(insertUiEvent: InsertTerapisUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}

fun Terapis.toInsertUIEvent(): InsertTerapisUiState = InsertTerapisUiState(
    insertUiEvent = this.toInsertUiEvent()
)

fun Terapis.toInsertUiEvent(): InsertTerapisUiEvent = InsertTerapisUiEvent(
    idTerapis = id_terapis,
    namaTerapis = nama_terapis,
    spesialisasi = spesialisasi,
    nomorIzinPraktik = nomor_izin_praktik
)