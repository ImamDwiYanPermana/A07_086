package com.example.ujian_terapi.ui.viewModel.JenisTerapi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_terapi.data.model.JenisTerapi
import com.example.ujian_terapi.data.repository.jenisTerapiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailJenisTerapiUiState {
data class Success(val jenisTerapi: JenisTerapi) : DetailJenisTerapiUiState()
object Error : DetailJenisTerapiUiState()
object Loading : DetailJenisTerapiUiState()
}

class DetailJenisTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisTerapiRepository: jenisTerapiRepository
) : ViewModel() {

    // Mengambil ID Jenis Terapi dari SavedStateHandle
    private val _idJenisTerapi: Int = checkNotNull(savedStateHandle["idJenisTerapi"])

    // StateFlow untuk mengelola UI State
    private val _detailJtUiState = MutableStateFlow<DetailJenisTerapiUiState>(DetailJenisTerapiUiState.Loading)
    val detailUiState: StateFlow<DetailJenisTerapiUiState> = _detailJtUiState

    // Inisialisasi ViewModel
    init {
        getDetailJenisTerapi()
    }

    // Fungsi untuk mengambil detail Jenis Terapi berdasarkan ID
    fun getDetailJenisTerapi() {
        viewModelScope.launch {
            try {
                _detailJtUiState.value = DetailJenisTerapiUiState.Loading
                val jenisTerapi = jenisTerapiRepository.getJenisTerapiById(_idJenisTerapi.toString())
                if (jenisTerapi != null) {
                    _detailJtUiState.value = DetailJenisTerapiUiState.Success(jenisTerapi)
                } else {
                    _detailJtUiState.value = DetailJenisTerapiUiState.Error
                }
            } catch (e: Exception) {
                _detailJtUiState.value = DetailJenisTerapiUiState.Error
            }
        }
    }
}