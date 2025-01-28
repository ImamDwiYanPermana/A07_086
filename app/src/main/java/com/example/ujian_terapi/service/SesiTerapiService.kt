package com.example.ujian_terapi.service

import com.example.ujian_terapi.data.model.SesiTerapi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SesiTerapiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacaSesiTerapi.php")
    suspend fun getSesiTerapi(): List<SesiTerapi>

    @GET("baca1SesiTerapi.php/{id_sesi}")
    suspend fun getSesiTerapiById(@Query("id_sesi") idSesi: Int): SesiTerapi

    @POST("insertSesiTerapi.php")
    suspend fun insertSesiTerapi(@Body sesiTerapi: SesiTerapi)

    @PUT("editSesiTerapi.php")
    suspend fun updateSesiTerapi(@Query("id_sesi") idSesi: Int, @Body sesiTerapi: SesiTerapi)

    @DELETE("deleteSesiTerapi.php")
    suspend fun deleteSesiTerapi(@Query("id_sesi") idSesi: Int): Response<Void>
}
