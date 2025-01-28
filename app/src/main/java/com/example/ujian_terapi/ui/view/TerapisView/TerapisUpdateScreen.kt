package com.example.ujian_terapi.ui.view.TerapisView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujian_terapi.navigation.DestinasiNavigasi
import com.example.ujian_terapi.ui.ConstumeAppBarr.CostumeTopAppBar
import com.example.ujian_terapi.ui.viewModel.Terapis.UpdateTerapisViewModel
import com.example.ujian_terapi.ui.viewModel.Terapis.toTerapis
import com.example.ujian_terapi.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateTerapis : DestinasiNavigasi {
    override val route = "update_terapis"
    const val idTerapis = "id_terapis"
    val routeWithArg = "$route/{$idTerapis}"
    override val titleRes = "Update Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerapisUpdateScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiState = viewModel.uiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTerapis.titleRes,
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
            // Entry Terapis Form Body
            EntryBodyTerapis(
                insertUiState = uiState.value,
                onTerapisValueChange = viewModel::updateTerapisState,
                onSaveClick = {
                    uiState.value.insertUiEvent?.let { insertUiEvent ->
                        coroutineScope.launch {
                            // Update Therapist data
                            viewModel.updateTerapis(
                                idTerapis = viewModel.idTerapis,
                                terapis = insertUiEvent.toTerapis()
                            )
                            // Navigate back after save
                            navigateBack()
                        }
                    }
                }
            )
        }
    }
}
