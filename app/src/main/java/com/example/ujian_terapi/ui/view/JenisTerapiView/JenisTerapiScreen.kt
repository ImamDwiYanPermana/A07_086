package com.example.ujian_terapi.ui.view.JenisTerapiView

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujian_terapi.R
import com.example.ujian_terapi.data.model.Terapis
import com.example.ujian_terapi.navigation.DestinasiNavigasi
import com.example.ujian_terapi.ui.ConstumeAppBarr.CostumeTopAppBar
import com.example.ujian_terapi.ui.ConstumeAppBarr.MenuButton
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.HomeJTUiState
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.JenisTerapiViewModel

object DestinasiHomeJenisTerapi : DestinasiNavigasi {
    override val route = "homeJenis"
    override val titleRes = "Home Jenis Terapi"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenJenisTerapi(
    navigateToPasien: () -> Unit,
    navigateToTerapis: () -> Unit,
    navigateToJenisTerapi: () -> Unit,
    navigateToSesiTerapi: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    navigateToAddJenisTerapi: () -> Unit,
    navigateToDetailJenisTerapi: (Int) -> Unit,
    navigateToUpdateTerapis: (String) -> Unit,

    viewModel: JenisTerapiViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Jenis Terapi",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getJenisTerapi()
                }
            )
        },


        floatingActionButton = {

            FloatingActionButton(
                onClick = navigateToJenisTerapi,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "JenisTerapis")
            }
            FloatingActionButton(
                onClick = navigateToAddJenisTerapi,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")

            }
        },
        bottomBar = {
            MenuButton(
                onPasienClick = navigateToPasien,
                onTerapisClick = navigateToTerapis,
                onJenisTerapiClick = navigateToJenisTerapi,
                onSesiTerapiClick = navigateToSesiTerapi,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

    ) { innerPadding ->
        JenisTerapiStatus(
            uiState = viewModel.jterapisUIState,
            retryAction = { viewModel.getJenisTerapi() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = navigateToDetailJenisTerapi,
            onDeleteClick = { id ->
                viewModel.deleteJenisTerapi(id)
                viewModel.getJenisTerapi() // Refresh data
            }
        )
    }

}

@Composable
fun JenisTerapiStatus(
    uiState: HomeJTUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Int) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    when (uiState) {
        is HomeJTUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeJTUiState.Success -> {
            if (uiState.jenisTerapi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data jenis terapi")
                }
            } else {
                JenisTerapiList(
                    jenisTerapiList = uiState.jenisTerapi,
                    onDeleteClick = onDeleteClick,
                    onDetailClick = onDetailClick
                )
            }
        }
        is HomeJTUiState.Error -> OnError(retryAction = retryAction, modifier = modifier.fillMaxSize())
        else -> {}
    }
}

@Composable
fun JenisTerapiList(
    jenisTerapiList: List<Terapis>,
    onDeleteClick: (Int) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(jenisTerapiList) { jenisTerapi ->
            JenisTerapiCard(
                jenisTerapi = jenisTerapi,
                onDeleteClick = { onDeleteClick(jenisTerapi.id_terapis) },
                onDetailClick = { onDetailClick(jenisTerapi.id_terapis) }
            )
        }
    }
}

@Composable
fun JenisTerapiCard(
    jenisTerapi: Terapis,
    onDeleteClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onDetailClick),
        elevation = CardDefaults.cardElevation(8.dp)
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
                    text = jenisTerapi.nama_terapis,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
            Text(text = "Spesialisasi: ${jenisTerapi.spesialisasi}")
            Text(text = "Nomor Izin Praktik: ${jenisTerapi.nomor_izin_praktik}")
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.eror),
            contentDescription = null
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = stringResource(R.string.retry))
        }
    }
}