package com.example.ujian_terapi.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienInsertViewModel
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasienEntryScreen(
    viewModel: PasienInsertViewModel,
    onNavigateBack: () -> Unit,       // Navigasi ke halaman sebelumnya
    onNavigateToDetails: (Int) -> Unit, // Navigasi ke halaman detail dengan ID pasien
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Pasien") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            PasienInputForm(
                uiState = viewModel.uiState,
                onValueChange = viewModel::updatePasienState
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val newId = viewModel.insertPasien() // Menyimpan data dan mendapatkan ID pasien baru
                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }

    // Dialog konfirmasi setelah data berhasil disimpan
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Sukses") },
            text = { Text("Data pasien berhasil disimpan.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        // Navigasi ke halaman detail pasien setelah data berhasil disimpan
                        val savedId = viewModel.getLastInsertedId() // Ambil ID pasien yang terakhir disimpan
                        onNavigateToDetails(savedId)
                    }
                ) {
                    Text("Lihat Detail")
                }
            }
        )
    }
}

@Composable
fun PasienInputForm(
    uiState: PasienUiEvent,
    onValueChange: (PasienUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = uiState.nama,
            onValueChange = { onValueChange(uiState.copy(nama = it)) },
            label = { Text("Nama Pasien") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.alamat,
            onValueChange = { onValueChange(uiState.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.nomorTelepon,
            onValueChange = { onValueChange(uiState.copy(nomorTelepon = it)) },
            label = { Text("Nomor Telepon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.tanggalLahir,
            onValueChange = { onValueChange(uiState.copy(tanggalLahir = it)) },
            label = { Text("Tanggal Lahir") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.riwayatMedikal,
            onValueChange = { onValueChange(uiState.copy(riwayatMedikal = it)) },
            label = { Text("Riwayat Medikal") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
    }
}
