package com.example.ujian_terapi.data.repository

import com.example.ujian_terapi.data.model.JenisTerapi
import com.example.ujian_terapi.service.JenisTerapiService
import okio.IOException


interface jenisTerapiRepository {
    suspend fun getJenisTerapi(): List<JenisTerapi>
    suspend fun insertJenisTerapi(jenisTerapi: JenisTerapi)
    suspend fun updateJenisTerapi(idJenis: String, jenisTerapi: JenisTerapi)
    suspend fun deleteJenisTerapi(idJenis: String)
    suspend fun getJenisTerapiById(idJenis: String): JenisTerapi
}

class NetworkJenisTerapiRepository(
    private val jenisTerapiApiService : JenisTerapiService
): jenisTerapiRepository{
    override suspend fun getJenisTerapi(): List<JenisTerapi> = jenisTerapiApiService.getJenisTerapi()

    override suspend fun insertJenisTerapi(jenisTerapi: JenisTerapi) {
       jenisTerapiApiService.insertJenisTerapi(jenisTerapi)
    }

    override suspend fun updateJenisTerapi(idJenis: String, jenisTerapi: JenisTerapi) {
        jenisTerapiApiService.updateJenisTerapi(idJenis, jenisTerapi)
    }

    override suspend fun deleteJenisTerapi(idJenis: String) {
        try {
            val response = jenisTerapiApiService.deleteJenisTerapi(idJenis)
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


    override suspend fun getJenisTerapiById(idJenis: String): JenisTerapi {
       return jenisTerapiApiService.getJenisTerapiById(idJenis)
    }

}