package com.example.ujian_terapi.ui.viewModel.Pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.Pasien
import com.example.ujian_terapi.data.repository.pasienRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class PasienUiState {
    data class Success(val pasien: List<Pasien>) : PasienUiState()
    object Error : PasienUiState()
    object Loading : PasienUiState()
}

class PasienViewModel(private val pasienRepository: pasienRepository) : ViewModel() {
    var pasienUiState: PasienUiState by mutableStateOf(PasienUiState.Loading)
        private set

    init {
        getPasien()
    }

    fun getPasien() {
        viewModelScope.launch {
            pasienUiState = PasienUiState.Loading
            pasienUiState = try {
                PasienUiState.Success(pasienRepository.getPasien())
            } catch (e: IOException) {
                PasienUiState.Error
            } catch (e: HttpException) {
                PasienUiState.Error
            }
        }
    }

    fun deletePasien(idPasien: Int) {
        viewModelScope.launch {
            try {
                pasienRepository.deletePasien(idPasien)
                getPasien() // Refresh list after deletion
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
