package com.example.ujian_terapi.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujian_terapi.navigation.DestinasiNavigasi
import com.example.ujian_terapi.ui.ConstumeAppBarr.CostumeTopAppBar
import com.example.ujian_terapi.ui.viewModel.Pasien.UpdatePasienViewModel
import com.example.ujian_terapi.ui.viewModel.Pasien.toPasien
import com.example.ujian_terapi.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update pasien"
    const val idpasien = "idpasien"
    val routesWithArg = "$route/{$idpasien}"
    override val titleRes = "Update Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasienView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePasienViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiState = viewModel.uiState

    // Format tanggal lahir yang diterima (UTC format)
    val formattedTanggalLahir = remember(uiState.insertUiEvent?.tanggalLahir) {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(uiState.insertUiEvent?.tanggalLahir ?: "")
            parsedDate?.let { format.format(it) } ?: uiState.insertUiEvent?.tanggalLahir // Handling null or invalid date
        } catch (e: Exception) {
            uiState.insertUiEvent?.tanggalLahir ?: "" // If parsing fails, keep the original or empty string
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Entry Pasien Form Body
            EntryBodyPasien(
                insertUiState = uiState.copy(
                    insertUiEvent = uiState.insertUiEvent?.copy(tanggalLahir = formattedTanggalLahir) ?: uiState.insertUiEvent
                ),
                onPasienValueChange = viewModel::updatePasienState,
                onSaveClick = {
                    uiState.insertUiEvent?.let { insertUiEvent ->
                        coroutineScope.launch {
                            viewModel.updatePasien(
                                idPasien = viewModel.idPasien,
                                pasien = insertUiEvent.toPasien()
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}