package com.example.ujian_terapi.ui.viewModel.JenisTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.JenisTerapi
import com.example.ujian_terapi.data.repository.jenisTerapiRepository
import kotlinx.coroutines.launch

class JenisTerapiInsertViewModel(private val jenisTerapiRepository: jenisTerapiRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertJenisTerapiUiState())
        private set

    // Fungsi untuk memperbarui state form
    fun updateInsertJenisTerapiState(insertUiEvent: InsertJenisTerapiUiEvent) {
        uiState = InsertJenisTerapiUiState(insertUiEvent = insertUiEvent)
    }

    // Fungsi untuk menyimpan data jenis terapi
    fun insertJenisTerapi() {
        viewModelScope.launch {
            try {
                val jenisTerapi = uiState.insertUiEvent.toJenisTerapi()
                jenisTerapiRepository.insertJenisTerapi(jenisTerapi)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// State untuk UI form
data class InsertJenisTerapiUiState(
    val insertUiEvent: InsertJenisTerapiUiEvent = InsertJenisTerapiUiEvent()
)

// Event untuk mengelola input form
data class InsertJenisTerapiUiEvent(
    val idJenisTerapi: Int = 0,
    val namaJenisTerapi: String = "",
    val deskripsiTerapi: String = ""
)

// Fungsi untuk mengubah InsertJenisTerapiUiEvent menjadi objek JenisTerapi
fun InsertJenisTerapiUiEvent.toJenisTerapi(): JenisTerapi = JenisTerapi(
    id_jenis_terapi = idJenisTerapi,
    nama_jenis_terapi = namaJenisTerapi,
    deskripsi_terapi = deskripsiTerapi
)

// Fungsi untuk mengubah objek JenisTerapi ke UI State
fun JenisTerapi.toUiStateJenisTerapi(): InsertJenisTerapiUiState = InsertJenisTerapiUiState(
    insertUiEvent = toInsertJenisTerapiUiEvent()
)

// Fungsi untuk mengubah objek JenisTerapi ke InsertJenisTerapiUiEvent
fun JenisTerapi.toInsertJenisTerapiUiEvent(): InsertJenisTerapiUiEvent = InsertJenisTerapiUiEvent(
    idJenisTerapi = id_jenis_terapi,
    namaJenisTerapi = nama_jenis_terapi,
    deskripsiTerapi = deskripsi_terapi
)