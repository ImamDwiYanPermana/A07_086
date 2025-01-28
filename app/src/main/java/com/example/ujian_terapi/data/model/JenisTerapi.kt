package com.example.ujian_terapi.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JenisTerapi(
    val id_jenis_terapi: Int,
    val nama_jenis_terapi: String,
    val deskripsi_terapi: String
)

@Serializable
data class JenisTerapiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: JenisTerapi
)

@Serializable
data class JenisTerapiResponse(
    val status: Boolean,
    val message: String,
    val data: List<JenisTerapi>
)
