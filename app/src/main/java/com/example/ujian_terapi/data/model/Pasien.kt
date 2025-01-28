package com.example.ujian_terapi.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Pasien(
    val id_pasien: Int,
    val nama_pasien: String,
    val alamat: String,
    val nomor_telepon: String,
    val tanggal_lahir: String,
    val riwayat_medikal: String?
)

