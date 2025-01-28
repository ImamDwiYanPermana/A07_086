package com.example.ujian_terapi.service

import com.example.ujian_terapi.data.model.JenisTerapi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface JenisTerapiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacaJenis.php")
    suspend fun getJenisTerapi(): List<JenisTerapi>

    @GET("baca1Jenis.php/{id_jenis_terapi}")
    suspend fun getJenisTerapiById(@Query("id_jenis_terapi") idJenis: String): JenisTerapi

    @POST("insertJenis.php")
    suspend fun insertJenisTerapi(@Body jenisTerapi: JenisTerapi)

    @PUT("editJenis.php")
    suspend fun updateJenisTerapi(@Query("id_jenis_terapi") idJenis: String, @Body jenisTerapi: JenisTerapi
    )

    @DELETE("deleteJenis.php")
    suspend fun deleteJenisTerapi(@Query("id_jenis_terapi") idJenis: String): Response<Void>
}