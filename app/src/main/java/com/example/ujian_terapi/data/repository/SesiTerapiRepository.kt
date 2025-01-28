package com.example.ujian_terapi.data.repository

import com.example.ujian_terapi.data.model.SesiTerapi
import com.example.ujian_terapi.service.SesiTerapiService
import okio.IOException

interface sesiTerapiRepository {
    suspend fun getSesiTerapi(): List<SesiTerapi>
    suspend fun insertSesiTerapi(sesiTerapi: SesiTerapi)
    suspend fun updateSesiTerapi(idSesi: Int, sesiTerapi: SesiTerapi)
    suspend fun deleteSesiTerapi(idSesi: Int)
    suspend fun getSesiTerapiById(idSesi: Int):SesiTerapi
}

class NetworkSesiTerapiRepository(
    private val sesiTerapiApiService: SesiTerapiService
): sesiTerapiRepository{
    override suspend fun getSesiTerapi(): List<SesiTerapi> = sesiTerapiApiService.getSesiTerapi()

    override suspend fun insertSesiTerapi(sesiTerapi: SesiTerapi) {
        sesiTerapiApiService.insertSesiTerapi(sesiTerapi)
    }

    override suspend fun updateSesiTerapi(idSesi: Int, sesiTerapi: SesiTerapi) {
      sesiTerapiApiService.updateSesiTerapi(idSesi, sesiTerapi)
    }

    override suspend fun deleteSesiTerapi(idSesi: Int) {
        try {
            val response = sesiTerapiApiService.deleteSesiTerapi(idSesi)
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

    override suspend fun getSesiTerapiById(idSesi: Int): SesiTerapi {
      return sesiTerapiApiService.getSesiTerapiById(idSesi)
    }

}