package com.example.ujian_terapi.ui.viewModel.Pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.repository.pasienRepository
import kotlinx.coroutines.launch

class PasienDetailViewModel(
    private val pasienRepository: pasienRepository,
    private val idPasien: Int
) : ViewModel() {
    var detailUiState: PasienUiState by mutableStateOf(PasienUiState.Loading)
        private set

    init {
        getPasienById()
    }

    private fun getPasienById() {
        viewModelScope.launch {
            detailUiState = try {
                val pasien = pasienRepository.getPasienById(idPasien)
                PasienUiState.Success(listOf(pasien))
            } catch (e: Exception) {
                PasienUiState.Error
            }
        }
    }
}