package com.example.ujian_terapi.ui.viewModel.Terapis

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.Terapis
import com.example.ujian_terapi.data.repository.terapisRepository
import com.example.ujian_terapi.ui.view.TerapisView.DestinasiDetailTerapis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailTerapisUiState {
    data class Success(val terapis: Terapis) : DetailTerapisUiState()
    object Error : DetailTerapisUiState()
    object Loading : DetailTerapisUiState()
}

class TerapisDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val terapisRepository: terapisRepository
) : ViewModel() {

    internal val _idTerapis: Int = checkNotNull(savedStateHandle[DestinasiDetailTerapis.idTerapis])

    private val _detailUiState = MutableStateFlow<DetailTerapisUiState>(DetailTerapisUiState.Loading)
    val detailUiState: StateFlow<DetailTerapisUiState> = _detailUiState

    init {
        getDetailTerapis()
    }

    fun getDetailTerapis() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailTerapisUiState.Loading
                val terapis = terapisRepository.getTerapisById(_idTerapis.toString())
                if (terapis != null) {
                    _detailUiState.value = DetailTerapisUiState.Success(terapis)
                } else {
                    _detailUiState.value = DetailTerapisUiState.Error
                }
            } catch (e: Exception) {
                _detailUiState.value = DetailTerapisUiState.Error
            }
        }
    }
}

fun Terapis.toDetailUiEvent(): InsertTerapisUiEvent {
    return InsertTerapisUiEvent(
        idTerapis = id_terapis,
        namaTerapis = nama_terapis,
        spesialisasi = spesialisasi,
        nomorIzinPraktik = nomor_izin_praktik
    )
}