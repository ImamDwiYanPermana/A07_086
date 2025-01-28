package com.example.ujian_terapi.ui.viewModel.SesiTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.SesiTerapi
import com.example.ujian_terapi.data.repository.sesiTerapiRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class SesiTerapiUiState {
    data class Success(val sesiTerapi: List<SesiTerapi>) : SesiTerapiUiState()
    object Error : SesiTerapiUiState()
    object Loading : SesiTerapiUiState()
}

class SesiTerapiViewModel(private val sesiTerapiRepository: sesiTerapiRepository) : ViewModel() {
    var sesiTerapiUiState: SesiTerapiUiState by mutableStateOf(SesiTerapiUiState.Loading)
        private set

    init {
        getSesiTerapi()
    }

    fun getSesiTerapi() {
        viewModelScope.launch {
            sesiTerapiUiState = SesiTerapiUiState.Loading
            sesiTerapiUiState = try {
                SesiTerapiUiState.Success(sesiTerapiRepository.getSesiTerapi())
            } catch (e: IOException) {
                SesiTerapiUiState.Error
            } catch (e: HttpException) {
                SesiTerapiUiState.Error
            }
        }
    }

    fun deleteSesiTerapi(idSesi: Int) {
        viewModelScope.launch {
            try {
                sesiTerapiRepository.deleteSesiTerapi(idSesi)
                getSesiTerapi()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}