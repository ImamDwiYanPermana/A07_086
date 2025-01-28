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

sealed class JenisTerapiUiState {
    data class Success(val jenisTerapi: List<JenisTerapi>) : JenisTerapiUiState()
    object Error : JenisTerapiUiState()
    object Loading : JenisTerapiUiState()
}

class JenisTerapiViewModel(private val jenisTerapiRepository: jenisTerapiRepository) : ViewModel() {
    var jenisTerapiUiState: JenisTerapiUiState by mutableStateOf(JenisTerapiUiState.Loading)
        private set

    init {
        getJenisTerapi()
    }

    fun getJenisTerapi() {
        viewModelScope.launch {
            jenisTerapiUiState = JenisTerapiUiState.Loading
            jenisTerapiUiState = try {
                JenisTerapiUiState.Success(jenisTerapiRepository.getJenisTerapi())
            } catch (e: IOException) {
                JenisTerapiUiState.Error
            } catch (e: HttpException) {
                JenisTerapiUiState.Error
            }
        }
    }

    fun deleteJenisTerapi(idJenis: Int) {
        viewModelScope.launch {
            try {
                jenisTerapiRepository.deleteJenisTerapi(idJenis)
                getJenisTerapi()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}