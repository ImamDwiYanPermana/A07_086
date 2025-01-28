package com.example.ujian_terapi.ui.viewModel.Pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.repository.pasienRepository
import com.example.ujian_terapi.ui.componen.toPasien
import kotlinx.coroutines.launch

data class PasienUiEvent(
    val nama: String = "",
    val alamat: String = "",
    val nomorTelepon: String = "",
    val tanggalLahir: String = "",
    val riwayatMedikal: String = "",
    val idPasien: Int = 0
) {

}

class PasienInsertViewModel(
    private val pasienRepository: pasienRepository
) : ViewModel() {
    var uiState by mutableStateOf(PasienUiEvent())
        private set

    fun updatePasienState(event: PasienUiEvent) {
        uiState = event
    }

    suspend fun insertPasien() {
        viewModelScope.launch {
            try {
                pasienRepository.insertPasien(uiState.toPasien())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}