package com.example.ujian_terapi.ui.viewModel.JenisTerapi

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.JenisTerapi
import com.example.ujian_terapi.data.repository.jenisTerapiRepository
import kotlinx.coroutines.launch

class JenisTerapiUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisTerapiRepository: jenisTerapiRepository
) : ViewModel() {

    val idJenisTerapi: Int = checkNotNull(savedStateHandle["idJenisTerapi"])

    var uiState = mutableStateOf(UpdateJenisTerapiUiState())

    init {
        getJenisTerapi()
    }

    // Fungsi untuk mendapatkan data Jenis Terapi berdasarkan ID
    private fun getJenisTerapi() {
        viewModelScope.launch {
            try {
                val jenisTerapi = jenisTerapiRepository.getJenisTerapiById(idJenisTerapi.toString())
                jenisTerapi?.let {
                    uiState.value = it.toUiState()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk memperbarui data Jenis Terapi
    fun updateJenisTerapi(jenisTerapi: JenisTerapi) {
        viewModelScope.launch {
            try {
                jenisTerapiRepository.updateJenisTerapi(idJenisTerapi.toString(), jenisTerapi)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk memperbarui UI State
    fun updateJenisTerapiState(updateUiEvent: UpdateJenisTerapiUiEvent) {
        uiState.value = uiState.value.copy(updateUiEvent = updateUiEvent)
    }
}

// Data class untuk UI State
data class UpdateJenisTerapiUiState(
    val updateUiEvent: UpdateJenisTerapiUiEvent = UpdateJenisTerapiUiEvent()
)

// Data class untuk Event Update Jenis Terapi
data class UpdateJenisTerapiUiEvent(
    val idJenisTerapi: Int = 0,
    val namaJenisTerapi: String = "",
    val deskripsiJenisTerapi: String = ""
)

// Extension function untuk mengubah JenisTerapi menjadi UI State
fun JenisTerapi.toUiState(): UpdateJenisTerapiUiState = UpdateJenisTerapiUiState(
    updateUiEvent = UpdateJenisTerapiUiEvent(
        idJenisTerapi = this.id_jenis_terapi,
        namaJenisTerapi = this.nama_jenis_terapi,
        deskripsiJenisTerapi = this.deskripsi_terapi
    )
)