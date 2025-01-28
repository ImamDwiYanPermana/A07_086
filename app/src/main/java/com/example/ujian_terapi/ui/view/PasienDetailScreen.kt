package com.example.ujian_terapi.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienDetailViewModel
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasienDetailScreen(
    viewModel: PasienDetailViewModel,
    onNavigateBack: () -> Unit,
    onEditClick: (Int) -> Unit, // Tambahkan ID pasien untuk navigasi edit
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Pasien") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali")
                    }
                },
                actions = {
                    val pasienId = (viewModel.detailUiState as? PasienUiState.Success)
                        ?.pasien?.firstOrNull()?.id_pasien
                    if (pasienId != null) {
                        IconButton(onClick = { onEditClick(pasienId) }) {
                            Icon(Icons.Default.Edit, "Edit")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val uiState = viewModel.detailUiState) {
            is PasienUiState.Loading -> {
                LoadingScreen(modifier = modifier.fillMaxSize())
            }
            is PasienUiState.Success -> {
                val pasien = uiState.pasien.firstOrNull()
                if (pasien != null) {
                    Column(
                        modifier = modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        DetailSection(title = "Nama", content = pasien.nama_pasien)
                        DetailSection(title = "Alamat", content = pasien.alamat)
                        DetailSection(title = "Nomor Telepon", content = pasien.nomor_telepon)
                        DetailSection(title = "Tanggal Lahir", content = pasien.tanggal_lahir)
                        DetailSection(title = "Riwayat Medikal", content = pasien.riwayat_medikal.toString())
                    }
                }
            }
            is PasienUiState.Error -> {
                ErrorScreen(modifier = modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
