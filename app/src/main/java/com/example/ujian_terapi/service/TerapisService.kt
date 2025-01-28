package com.example.ujian_terapi.service
import com.example.ujian_terapi.data.model.Pasien
import com.example.ujian_terapi.data.model.Terapis
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TerapisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacaTerapis.php")
    suspend fun getTerapis(): List<Terapis>

    @GET("baca1Terapis.php/{id_terapis}")
    suspend fun getTerapisById(@Query("id_terapis") idTerapis: Int): Terapis

    @POST("insertTerapis.php")
    suspend fun insertTerapis(@Body terapis: Terapis)

    @PUT("editTerapis.php")
    suspend fun updateTerapis(@Query("id_terapis") idTerapis: Int, @Body terapis: Terapis)

    @DELETE("deleteTerapis.php")
    suspend fun deleteTerapis(@Query("id_terapis") idTerapis: Int): Response<Void>
}