package com.example.ujian_terapi.service

import com.example.ujian_terapi.data.model.Pasien
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PasienService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacaPasien.php")
    suspend fun getPasien(): List<Pasien>

    @GET("baca1Pasien.php/{id_pasien}")
    suspend fun getPasienById(@Query("id_pasien") idPasien: Int): Pasien

    @POST("insertPasien.php")
    suspend fun insertPasien(@Body pasien: Pasien)

    @PUT("editPasien.php")
    suspend fun updatePasien(@Query("id_pasien") idPasien: Int, @Body pasien: Pasien)

    @DELETE("deletePasien.php")
    suspend fun deletePasien(@Query("id_pasien") idPasien: Int): Response<Void>
}