package com.example.ujian_terapi.ui.viewModel.Terapis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.Terapis
import com.example.ujian_terapi.data.repository.terapisRepository
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

sealed class TerapisUiState {
    data class Success(val terapis: List<Terapis>) : TerapisUiState()
    object Error : TerapisUiState()
    object Loading : TerapisUiState()
}

class TerapisViewModel(private val terapisRepository: terapisRepository) : ViewModel() {
    var terapisUiState: TerapisUiState by mutableStateOf(TerapisUiState.Loading)
        private set

    init {
        getTerapis()
    }

    fun getTerapis() {
        viewModelScope.launch {
            terapisUiState = TerapisUiState.Loading
            terapisUiState = try {
                TerapisUiState.Success(terapisRepository.getTerapis())
            } catch (e: IOException) {
                TerapisUiState.Error
            } catch (e: HttpException) {
                TerapisUiState.Error
            }
        }
    }

    fun deleteTerapis(idTerapis: Int) {
        viewModelScope.launch {
            try {
                terapisRepository.deleteTerapis(idTerapis)
                getTerapis() // Refresh list after deletion
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
