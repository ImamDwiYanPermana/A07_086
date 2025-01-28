package com.example.ujian_terapi.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Terapis(
    val id_terapis: Int,
    val nama_terapis: String,
    val spesialisasi: String,
    val nomor_izin_praktik: String
)
