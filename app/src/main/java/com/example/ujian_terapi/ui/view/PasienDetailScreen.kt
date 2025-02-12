package com.example.ujian_terapi.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujian_terapi.data.model.Pasien
import com.example.ujian_terapi.navigation.DestinasiNavigasi
import com.example.ujian_terapi.ui.ConstumeAppBarr.CostumeTopAppBar
import com.example.ujian_terapi.ui.viewModel.Pasien.DetailUiState
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienDetailViewModel
import com.example.ujian_terapi.ui.viewModel.PenyediaViewModel
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail pasien"
    const val idpasien = "idpasien"
    val routeWithArg = "$route/{$idpasien}"
    override  val titleRes = "Detail Pasien"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPasienView(
    idPasien: Int,
    modifier: Modifier = Modifier,
    viewModel: PasienDetailViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {}, // Callback untuk tombol edit
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailPasien() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idPasien) }, // Navigasi ke halaman edit
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pasien"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        when (val state = detailUiState) {
            is DetailUiState.Loading -> {
                OnLoading(modifier = Modifier.fillMaxSize())
            }
            is DetailUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    ItemDetailPasien(pasien = state.pasien)
                }
            }
            is DetailUiState.Error -> {
                OnError(
                    retryAction = { viewModel.getDetailPasien() },
                    modifier = modifier.fillMaxSize()
                )
            }
            else -> {
                Text("Unexpected state")
            }
        }
    }
}


@Composable
fun ItemDetailPasien(pasien: Pasien) {
    val formattedTanggalLahir = remember(pasien.tanggal_lahir) {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(pasien.tanggal_lahir)
        parsedDate?.let { format.format(it) } ?: pasien.tanggal_lahir // Handle null case
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPasien(judul = "ID Pasien", isinya = pasien.id_pasien.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Nama", isinya = pasien.nama_pasien)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Alamat", isinya = pasien.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Nomor Telepon", isinya = pasien.nomor_telepon)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Tanggal Lahir", isinya = formattedTanggalLahir)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Riwayat Medikal", isinya = pasien.riwayat_medikal.toString())
        }
    }
}

@Composable
fun ComponentDetailPasien(judul: String, isinya: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}