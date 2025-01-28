package com.example.ujian_terapi.ui.viewModel.Pasien

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.Pasien
import com.example.ujian_terapi.data.repository.pasienRepository
import com.example.ujian_terapi.ui.view.DestinasiDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success(val pasien: Pasien) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}
class PasienDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pasienRepository: pasienRepository
) : ViewModel() {

    private val _idPasien: Int = checkNotNull(savedStateHandle[DestinasiDetail.idpasien])

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState

    init {
        getDetailPasien()
    }

    fun getDetailPasien() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailUiState.Loading
                val pasien = pasienRepository.getPasienById(_idPasien)
                if (pasien != null) {
                    _detailUiState.value = DetailUiState.Success(pasien)
                } else {
                    _detailUiState.value = DetailUiState.Error
                }
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}
fun Pasien.toDetailUiEvent(): InsertPasienUiEvent {
    return InsertPasienUiEvent(
        idPasien = id_pasien,
        namaPasien = nama_pasien,
        alamat = alamat,
        nomorTelepon = nomor_telepon,
        tanggalLahir = tanggal_lahir,
        riwayatMedikal = riwayat_medikal.toString()
    )
}