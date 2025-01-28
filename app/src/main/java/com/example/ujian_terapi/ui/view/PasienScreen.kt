package com.example.ujian_terapi.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ujian_terapi.data.model.Pasien
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienUiState
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienViewModel

@Composable
fun PasienScreen(
    navigateToEntry: () -> Unit,
    onDetailClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PasienViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedId by remember { mutableStateOf(-1) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToEntry) {
                Icon(Icons.Default.Add, "Tambah Pasien")
            }
        }
    ) { innerPadding ->
        when (val uiState = viewModel.pasienUiState) {
            is PasienUiState.Loading -> {
                LoadingScreen(modifier = modifier.fillMaxSize())
            }
            is PasienUiState.Success -> {
                PasienListScreen(
                    pasienList = uiState.pasien,
                    onDetailClick = onDetailClick,
                    onDeleteClick = { idPasien ->
                        selectedId = idPasien
                        showDialog = true
                    },
                    modifier = modifier.padding(innerPadding)
                )
            }
            is PasienUiState.Error -> {
                ErrorScreen(modifier = modifier.fillMaxSize())
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menghapus data pasien ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deletePasien(selectedId) // Logika hapus pasien
                        showDialog = false
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Tidak")
                }
            }
        )
    }
}

@Composable
fun PasienListScreen(
    pasienList: List<Pasien>,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(pasienList) { pasien ->
            PasienItem(
                pasien = pasien,
                onDetailClick = { onDetailClick(pasien.id_pasien) }, // Navigasi ke detail
                onDeleteClick = { onDeleteClick(pasien.id_pasien) } // Dialog hapus pasien
            )
        }
    }
}

@Composable
fun PasienItem(
    pasien: Pasien,
    onDetailClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetailClick() } // Klik untuk melihat detail
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = pasien.nama_pasien,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = pasien.nomor_telepon,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, "Hapus")
            }
        }
    }
}