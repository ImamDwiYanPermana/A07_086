package com.example.ujian_terapi.ui.viewModel.SesiTerapi

//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.ujian_terapi.data.model.JenisTerapi
//import com.example.ujian_terapi.data.model.Terapis
//import com.example.ujian_terapi.data.repository.jenisTerapiRepository
//import com.example.ujian_terapi.data.repository.sesiTerapiRepository
//import com.example.ujian_terapi.data.repository.terapisRepository
//import kotlinx.coroutines.launch

//class SesiTerapiInsertViewModel(
//    private val sesiTerapiRepository: sesiTerapiRepository,
//    private val terapisRepository: terapisRepository,
//    private val jenisTerapiRepository: jenisTerapiRepository
//) : ViewModel() {
//    var uiState by mutableStateOf(SesiTerapiUiEvent())
//        private set

//    var terapisList by mutableStateOf<List<Terapis>>(emptyList())
//        private set

//    var jenisTerapiList by mutableStateOf<List<JenisTerapi>>(emptyList())
//        private set

//    init {
//        loadTerapisList()
//        loadJenisTerapiList()
//    }

//    private fun loadTerapisList() {
//        viewModelScope.launch {
//            try {
//                terapisList = terapisRepository.getTerapis()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

//    private fun loadJenisTerapiList() {
//        viewModelScope.launch {
//            try {
//                jenisTerapiList = jenisTerapiRepository.getJenisTerapi()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

//    fun updateSesiTerapiState(event: SesiTerapiUiEvent) {
//        uiState = event
//    }
//
//    suspend fun insertSesiTerapi() {
//        viewModelScope.launch {
//            try {
//                sesiTerapiRepository.insertSesiTerapi(uiState.toSesiTerapi())
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//}