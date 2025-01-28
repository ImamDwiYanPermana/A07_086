package com.example.ujian_terapi.ui.viewModel

// PenyediaViewModel.kt
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ujian_terapi.TerapisApplication
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.JenisTerapiDetailViewModel
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.JenisTerapiInsertViewModel
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.JenisTerapiViewModel
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienDetailViewModel
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienInsertViewModel
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienViewModel
import com.example.ujian_terapi.ui.viewModel.SesiTerapi.SesiTerapiDetailViewModel
import com.example.ujian_terapi.ui.viewModel.SesiTerapi.SesiTerapiInsertViewModel
import com.example.ujian_terapi.ui.viewModel.SesiTerapi.SesiTerapiViewModel
import com.example.ujian_terapi.ui.viewModel.Terapis.TerapisDetailViewModel
import com.example.ujian_terapi.ui.viewModel.Terapis.TerapisInsertViewModel
import com.example.ujian_terapi.ui.viewModel.Terapis.TerapisViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Pasien ViewModels
        initializer {
            PasienViewModel(TerapiApplication().container.pasienRepository)
        }
        initializer {
            PasienInsertViewModel(TerapiApplication().container.pasienRepository)
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            val idPasien = checkNotNull(savedStateHandle["idPasien"]) as Int
            PasienDetailViewModel(
                TerapiApplication().container.pasienRepository,
                idPasien
            )
        }

        // Terapis ViewModels
        initializer {
            TerapisViewModel(TerapiApplication().container.terapisRepository)
        }
        initializer {
            TerapisInsertViewModel(TerapiApplication().container.terapisRepository)
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            val idTerapis = checkNotNull(savedStateHandle["idTerapis"]) as Int
            TerapisDetailViewModel(
                TerapiApplication().container.terapisRepository,
                idTerapis
            )
        }

        // JenisTerapi ViewModels
        initializer {
            JenisTerapiViewModel(TerapiApplication().container.jenisTerapiRepository)
        }
        initializer {
            JenisTerapiInsertViewModel(TerapiApplication().container.jenisTerapiRepository)
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            val idJenis = checkNotNull(savedStateHandle["idJenis"]) as Int
            JenisTerapiDetailViewModel(
                TerapiApplication().container.jenisTerapiRepository,
                idJenis
            )
        }

        // SesiTerapi ViewModels
        initializer {
            SesiTerapiViewModel(TerapiApplication().container.sesiTerapiRepository)
        }
        initializer {
            SesiTerapiInsertViewModel(
                TerapiApplication().container.sesiTerapiRepository,
                TerapiApplication().container.terapisRepository,
                TerapiApplication().container.jenisTerapiRepository
            )
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            val idSesi = checkNotNull(savedStateHandle["idSesi"]) as Int
            SesiTerapiDetailViewModel(
                TerapiApplication().container.sesiTerapiRepository,
                idSesi
            )
        }
    }
}

fun CreationExtras.TerapiApplication(): TerapisApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TerapisApplication)