package com.example.ujian_terapi.data.repository


import com.example.ujian_terapi.data.model.Terapis
import com.example.ujian_terapi.service.TerapisService
import okio.IOException

interface terapisRepository {
    suspend fun getTerapis(): List<Terapis>
    suspend fun insertTerapis(terapis: Terapis)
    suspend fun updateTerapis(idTerapis: String, terapis: Terapis)
    suspend fun deleteTerapis(idTerapis: Int)
    suspend fun getTerapisById(idTerapis: String): Terapis
}

class NetworkTerapisRepository(
    private val terapisApiService: TerapisService
): terapisRepository {
    override suspend fun getTerapis(): List<Terapis> = terapisApiService.getTerapis()

    override suspend fun insertTerapis(terapis: Terapis) {
        terapisApiService.insertTerapis(terapis)
    }

    override suspend fun updateTerapis(idTerapis: String, terapis: Terapis) {
        terapisApiService.updateTerapis(idTerapis, terapis)
    }

    override suspend fun deleteTerapis(idTerapis: Int) {
        try {
            val response = terapisApiService.deleteTerapis(idTerapis)
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
    override suspend fun getTerapisById(idTerapis: String): Terapis {
        return  terapisApiService.getTerapisById(idTerapis)
    }
}