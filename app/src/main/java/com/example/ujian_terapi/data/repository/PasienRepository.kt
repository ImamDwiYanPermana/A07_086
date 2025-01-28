package com.example.ujian_terapi.data.repository

import com.example.ujian_terapi.data.model.Pasien
import com.example.ujian_terapi.service.PasienService
import okio.IOException

interface pasienRepository {
    suspend fun getPasien(): List<Pasien>
    suspend fun insertPasien(pasien: Pasien)
    suspend fun updatePasien(idPasien: Int, pasien: Pasien)
    suspend fun deletePasien(idPasien: Int)
    suspend fun getPasienById(idPasien: Int):Pasien
}

class NetworkPasienRepository(
    private val pasienApiService: PasienService
): pasienRepository {
    override suspend fun getPasien(): List<Pasien> = pasienApiService.getPasien()

    override suspend fun insertPasien(pasien: Pasien) {
        pasienApiService.insertPasien(pasien)
    }

    override suspend fun updatePasien(idPasien: Int, pasien: Pasien) {
        pasienApiService.updatePasien(idPasien, pasien)
    }

    override suspend fun deletePasien(idPasien: Int) {
        try {
            val response = pasienApiService.deletePasien(idPasien)
            if (!response.isSuccessful) {
                throw IOException("gagal menghapus data Mahasiswa. HTTP kode: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun getPasienById(idPasien: Int): Pasien {
        return  pasienApiService.getPasienById(idPasien)
    }
}