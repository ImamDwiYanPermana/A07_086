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

sealed class HomeUiState {
    data class Success(val terapis: List<Terapis>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModelTerapis(private val terapisRepository: terapisRepository) : ViewModel() {
    var terapisUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getTerapis()
    }

    fun getTerapis() {
        viewModelScope.launch {
            terapisUIState = HomeUiState.Loading
            terapisUIState = try {
                HomeUiState.Success(terapisRepository.getTerapis())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteTerapis(idTerapis: Int) {
        viewModelScope.launch {
            try {
                terapisRepository.deleteTerapis(idTerapis)
                getTerapis() // Segarkan data setelah penghapusan
            } catch (e: IOException) {
                terapisUIState = HomeUiState.Error
            } catch (e: HttpException) {
                terapisUIState = HomeUiState.Error
            }
        }
    }
}