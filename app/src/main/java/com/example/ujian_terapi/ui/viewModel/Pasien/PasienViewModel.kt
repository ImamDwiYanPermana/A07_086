package com.example.ujian_terapi.ui.viewModel.Pasien

import android.util.Log
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

sealed class HomeUiState {
    data class Success(val pasien: List<Pasien>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModelPasien(private val pasienRepository: pasienRepository) : ViewModel() {
    var pasienUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        Log.d("HomeViewModelPasien", "Initializing HomeViewModelPasien...")
        getPasien()
    }

    fun getPasien() {
        viewModelScope.launch {
            Log.d("HomeViewModelPasien", "Fetching list of patients...")
            pasienUIState = HomeUiState.Loading
            pasienUIState = try {
                val data = pasienRepository.getPasien().data
                Log.d("HomeViewModelPasien", "Successfully fetched patients: ${data.size} items.")
                HomeUiState.Success(data)
            } catch (e: IOException) {
                Log.e("HomeViewModelPasien", "IO Exception while fetching patients: ${e.message}")
                HomeUiState.Error
            } catch (e: HttpException) {
                Log.e("HomeViewModelPasien", "HTTP Exception while fetching patients: ${e.message}")
                HomeUiState.Error
            }
        }
    }

    fun deletePasien(idPasien: Int) {
        viewModelScope.launch {
            Log.d("HomeViewModelPasien", "Attempting to delete patient with ID: $idPasien")
            try {
                pasienRepository.deletePasien(idPasien)
                Log.d("HomeViewModelPasien", "Successfully deleted patient with ID: $idPasien")
            } catch (e: IOException) {
                Log.e("HomeViewModelPasien", "IO Exception while deleting patient: ${e.message}")
                pasienUIState = HomeUiState.Error
            } catch (e: HttpException) {
                Log.e("HomeViewModelPasien", "HTTP Exception while deleting patient: ${e.message}")
                pasienUIState = HomeUiState.Error
            }
        }
    }
}