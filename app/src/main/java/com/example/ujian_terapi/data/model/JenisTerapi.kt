package com.example.ujian_terapi.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JenisTerapi(
    val id_jenis_terapi: Int,
    val nama_jenis_terapi: String,
    val deskripsi_terapi: String
)
