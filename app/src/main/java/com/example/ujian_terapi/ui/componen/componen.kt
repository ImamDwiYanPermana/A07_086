package com.example.ujian_terapi.ui.componen

import com.example.ujian_terapi.data.model.JenisTerapi
import com.example.ujian_terapi.data.model.Pasien
import com.example.ujian_terapi.data.model.SesiTerapi
import com.example.ujian_terapi.data.model.Terapis
import com.example.ujian_terapi.ui.viewModel.JenisTerapi.JenisTerapiUiEvent
import com.example.ujian_terapi.ui.viewModel.Pasien.PasienUiEvent
import com.example.ujian_terapi.ui.viewModel.SesiTerapi.SesiTerapiUiEvent
import com.example.ujian_terapi.ui.viewModel.Terapis.TerapisUiEvent

fun TerapisUiEvent.toTerapis() = Terapis(
    nama_terapis = namaTerapis,
    spesialisasi = spesialisasi,
    nomor_izin_praktik = nomorIzinPraktik,
    id_terapis = idTerapis,
)

fun JenisTerapiUiEvent.toJenisTerapi() = JenisTerapi(
    nama_jenis_terapi = namaJenisTerapi,
    deskripsi_terapi = deskripsiTerapi,
    id_jenis_terapi = idJenisTerapi
)

fun SesiTerapiUiEvent.toSesiTerapi() = SesiTerapi(
    id_pasien = idPasien,
    id_terapis = idTerapis,
    id_jenis_terapi = idJenisTerapi,
    tanggal_sesi = tanggalSesi,
    catatan_sesi = catatanSesi,
    id_sesi = idSesi
)

fun PasienUiEvent.toPasien() = Pasien(
    id_pasien = idPasien,
    nama_pasien = nama,
    alamat = alamat,
    nomor_telepon = nomorTelepon,
    tanggalLahir,
    riwayat_medikal = riwayatMedikal
)

