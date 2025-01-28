package com.example.ujian_terapi.ui.view.TerapisView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujian_terapi.data.model.Terapis
import com.example.ujian_terapi.ui.viewModel.Terapis.DetailTerapisUiState
import com.example.ujian_terapi.ui.viewModel.Terapis.TerapisDetailViewModel
import com.example.ujian_terapi.ui.ConstumeAppBarr.CostumeTopAppBar
import com.example.ujian_terapi.navigation.DestinasiNavigasi

object DestinasiDetailTerapis : DestinasiNavigasi {
    override val route = "detail_terapis"
    const val idTerapis = "idTerapis"
    val routeWithArg = "$route/{$idTerapis}"
    override val titleRes = "Detail Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerapisDetailScreen(
    navigateBack: () -> Unit,
    onEditClick: (Int) -> Unit = {},
    viewModel: TerapisDetailViewModel = viewModel(),  // ViewModel is instantiated here
    idTerapis: Int
) {
    // Collect the current UI state from the ViewModel
    val detailUiState by viewModel.detailUiState.collectAsState()

    // Scaffold is used to structure the screen, including top bar, content, and floating action button
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTerapis.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailTerapis() }  // Refreshes the details
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(viewModel._idTerapis) },  // Pass the idTerapis to edit
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Terapis")
            }
        }
    ) { innerPadding ->
        // The content body of the screen
        BodyDetailScreen(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState
        )
    }
}

@Composable
fun BodyDetailScreen(
    modifier: Modifier = Modifier,
    detailUiState: DetailTerapisUiState
) {
    when (detailUiState) {
        is DetailTerapisUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())  // Show loading spinner
        }
        is DetailTerapisUiState.Success -> {
            val terapis = detailUiState.terapis
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ItemDetailTerapis(terapis = terapis)  // Show the details of the therapist
            }
        }
        is DetailTerapisUiState.Error -> {
            OnError(modifier = modifier.fillMaxSize())  // Show error state with a retry button
        }
    }
}

@Composable
fun ItemDetailTerapis(terapis: Terapis) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Each field in the therapist's details is displayed here
            ComponentDetail(judul = "ID Terapis", isinya = terapis.id_terapis.toString())
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetail(judul = "Nama Terapis", isinya = terapis.nama_terapis)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetail(judul = "Spesialisasi", isinya = terapis.spesialisasi)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetail(judul = "Nomor Izin Praktik", isinya = terapis.nomor_izin_praktik)
        }
    }
}

@Composable
fun ComponentDetail(judul: String, isinya: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun OnLoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun OnError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Terjadi kesalahan! Silakan coba lagi.")
        Button(onClick = { /* Trigger retry logic from viewModel */ }) {
            Text(text = "Coba Lagi")
        }
    }
}
