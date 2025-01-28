package com.example.ujian_terapi.ui.viewModel.JenisTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.JenisTerapi
import com.example.ujian_terapi.data.repository.jenisTerapiRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeJTUiState {
    data class Success(val jenisTerapi: List<JenisTerapi>) : HomeJTUiState()
    object Error : HomeJTUiState()
    object Loading : HomeJTUiState()
}

class JenisTerapiViewModel(private val jenisTerapiRepository: jenisTerapiRepository) : ViewModel() {
    // State untuk mengelola UI
    var jterapisUIState: HomeJTUiState by mutableStateOf(HomeJTUiState.Loading)
        private set

    init {
        getJenisTerapi()
    }

    // Mendapatkan data jenis terapi
    fun getJenisTerapi() {
        viewModelScope.launch {
            jterapisUIState = HomeJTUiState.Loading // Set state ke Loading
            jterapisUIState = try {
                val response = jenisTerapiRepository.getJenisTerapi()
                HomeJTUiState.Success(response)
            } catch (e: IOException) {
                HomeJTUiState.Error
            } catch (e: HttpException) {
                HomeJTUiState.Error
            }
        }
    }

    // Menghapus jenis terapi berdasarkan ID
    fun deleteJenisTerapi(idJenisTerapi: Int) {
        viewModelScope.launch {
            try {
                jenisTerapiRepository.deleteJenisTerapi(idJenisTerapi.toString())
                getJenisTerapi() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                jterapisUIState = HomeJTUiState.Error
            } catch (e: HttpException) {
                jterapisUIState = HomeJTUiState.Error
            }
        }
    }
}