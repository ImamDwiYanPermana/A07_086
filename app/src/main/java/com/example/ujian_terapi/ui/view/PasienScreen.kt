package com.example.ujian_terapi.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujian_terapi.R
import com.example.ujian_terapi.data.model.Pasien
import com.example.ujian_terapi.navigation.DestinasiNavigasi
import com.example.ujian_terapi.ui.ConstumeAppBarr.CostumeTopAppBar
import com.example.ujian_terapi.ui.ConstumeAppBarr.MenuButton
import com.example.ujian_terapi.ui.viewModel.Pasien.HomeUiState
import com.example.ujian_terapi.ui.viewModel.Pasien.HomeViewModelPasien
import com.example.ujian_terapi.ui.viewModel.PenyediaViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DestinasiHome : DestinasiNavigasi {
    override val route = "home pasien"
    override val titleRes = "Home Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPasien(
    navigateToItemEntry: () -> Unit,
    navigateToPasien: () -> Unit, // Navigasi ke halaman Pasien
    navigateToTerapis: () -> Unit, // Navigasi ke halaman Terapis
    navigateToJenisTerapi: () -> Unit, // Navigasi ke halaman Jenis Terapi
    navigateToSesiTerapi: () -> Unit, // Navigasi ke halaman Sesi Terapi
    modifier: Modifier = Modifier,
    navigateToUpdatePasien: (String) -> Unit,

    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModelPasien = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPasien()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pasien")
            }
        },
        bottomBar = {
            MenuButton(
                onPasienClick = navigateToPasien,
                onTerapisClick = navigateToTerapis,
                onJenisTerapiClick = navigateToJenisTerapi,
                onSesiTerapiClick = navigateToSesiTerapi,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.pasienUIState,
            retryAction = { viewModel.getPasien() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { pasien ->
                viewModel.deletePasien(pasien.id_pasien)
                viewModel.getPasien()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pasien) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.pasien.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data pasien")
                }
            } else {
                PasienLayout(
                    pasien = homeUiState.pasien,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_pasien) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())

    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.eror), contentDescription = ""
        )

        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PasienLayout(
    pasien: List<Pasien>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pasien) -> Unit,
    onDeleteClick: (Pasien) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pasien) { pasien ->
            PasienCard(
                pasien = pasien,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pasien) },
                onDeleteClick = { onDeleteClick(pasien) }
            )
        }
    }
}

@Composable
fun PasienCard(
    pasien: Pasien,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pasien) -> Unit = {}
) {
    // Format tanggal menggunakan SimpleDateFormat
    val formattedTanggalLahir = remember(pasien.tanggal_lahir) {
        try {
            val utcFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            utcFormat.timeZone = TimeZone.getTimeZone("UTC")
            val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }.parse(pasien.tanggal_lahir)
            parsedDate?.let { utcFormat.format(it) } ?: pasien.tanggal_lahir
        } catch (e: Exception) {
            pasien.tanggal_lahir
        }
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pasien.nama_pasien,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(pasien) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }

            Text(
                text = "Alamat: ${pasien.alamat}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Nomor Telepon: ${pasien.nomor_telepon}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tanggal Lahir: $formattedTanggalLahir",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}