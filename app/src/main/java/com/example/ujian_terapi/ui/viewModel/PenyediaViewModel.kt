package com.example.ujian_terapi.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ujian_terapi.TerapisApplication
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.DetailJenisTerapiViewModel
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.JenisTerapiInsertViewModel
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.JenisTerapiUpdateViewModel
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.JenisTerapiViewModel
import com.example.ujian_terapi.ui.viewModel.Pasien.HomeViewModelPasien
import com.example.ujian_terapi.ui.viewModel.Pasien.InsertPasienViewModel
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienDetailViewModel
import com.example.ujian_terapi.ui.viewModel.Pasien.UpdatePasienViewModel
import com.example.ujian_terapi.ui.viewModel.Terapis.HomeViewModelTerapis
import com.example.ujian_terapi.ui.viewModel.Terapis.TerapisDetailViewModel
import com.example.ujian_terapi.ui.viewModel.Terapis.TerapisInsertViewModel
import com.example.ujian_terapi.ui.viewModel.Terapis.UpdateTerapisViewModel

// PenyediaViewModel.kt
object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModelPasien(aplikasiTerapi().container.pasienRepository) }
        initializer { InsertPasienViewModel(aplikasiTerapi().container.pasienRepository) }
        initializer { PasienDetailViewModel(createSavedStateHandle(),aplikasiTerapi().container.pasienRepository) }
        initializer { UpdatePasienViewModel(createSavedStateHandle(),aplikasiTerapi().container.pasienRepository) }
        initializer { HomeViewModelTerapis(aplikasiTerapi().container.terapisRepository) }
        initializer { TerapisInsertViewModel(aplikasiTerapi().container.terapisRepository) }
        initializer { TerapisDetailViewModel(createSavedStateHandle(),aplikasiTerapi().container.terapisRepository) }
        initializer { UpdateTerapisViewModel(createSavedStateHandle(),aplikasiTerapi().container.terapisRepository) }
        initializer { JenisTerapiViewModel(aplikasiTerapi().container.jenisTerapiRepository) }
        initializer { JenisTerapiInsertViewModel(aplikasiTerapi().container.jenisTerapiRepository) }
        initializer { DetailJenisTerapiViewModel(createSavedStateHandle(),aplikasiTerapi().container.jenisTerapiRepository) }
        initializer { JenisTerapiUpdateViewModel(createSavedStateHandle(),aplikasiTerapi().container.jenisTerapiRepository) }




    }
}

fun CreationExtras.aplikasiTerapi(): TerapisApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as TerapisApplication)