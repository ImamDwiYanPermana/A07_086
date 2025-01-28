package com.example.ujian_terapi.dependenciesinjection

import com.example.ujian_terapi.data.repository.NetworkJenisTerapiRepository
import com.example.ujian_terapi.data.repository.NetworkPasienRepository
import com.example.ujian_terapi.data.repository.NetworkSesiTerapiRepository
import com.example.ujian_terapi.data.repository.NetworkTerapisRepository
import com.example.ujian_terapi.data.repository.jenisTerapiRepository
import com.example.ujian_terapi.service.PasienService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.example.ujian_terapi.data.repository.pasienRepository
import com.example.ujian_terapi.data.repository.sesiTerapiRepository
import com.example.ujian_terapi.data.repository.terapisRepository
import com.example.ujian_terapi.service.JenisTerapiService
import com.example.ujian_terapi.service.SesiTerapiService
import com.example.ujian_terapi.service.TerapisService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Interface Umum
interface AppContainer {
    val sesiTerapiRepository: sesiTerapiRepository
    val jenisTerapiRepository: jenisTerapiRepository
    val terapisRepository: terapisRepository
    val pasienRepository: pasienRepository
}

// Container Sesi Terapi
class SesiTerapiContainer {
    private val baseUrl = "http://your-api/sesi-terapi/"
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val sesiTerapiService: SesiTerapiService by lazy {
        retrofit.create(SesiTerapiService::class.java)
    }

    val sesiTerapiRepository: sesiTerapiRepository by lazy {
        NetworkSesiTerapiRepository(sesiTerapiService)
    }
}

// Container Jenis Terapi
class JenisTerapiContainer {
    private val baseUrl = "http://your-api/jenis-terapi/"
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val jenisTerapiService: JenisTerapiService by lazy {
        retrofit.create(JenisTerapiService::class.java)
    }

    val jenisTerapiRepository: jenisTerapiRepository by lazy {
        NetworkJenisTerapiRepository(jenisTerapiService)
    }
}

// Container Terapis
class TerapisContainer {
    private val baseUrl = "http://your-api/terapis/"
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val terapisService: TerapisService by lazy {
        retrofit.create(TerapisService::class.java)
    }

    val terapisRepository: terapisRepository by lazy {
        NetworkTerapisRepository(terapisService)
    }
}

// Container Pasien
class PasienContainer {
    private val baseUrl = "http://your-api/pasien/"
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val pasienService: PasienService by lazy {
        retrofit.create(PasienService::class.java)
    }

    val pasienRepository: pasienRepository by lazy {
        NetworkPasienRepository(pasienService)
    }
}

// Container Utama (Opsional)
class MainAppContainer : AppContainer {
    private val sesiTerapiContainer = SesiTerapiContainer()
    private val jenisTerapiContainer = JenisTerapiContainer()
    private val terapisContainer = TerapisContainer()
    private val pasienContainer = PasienContainer()

    override val sesiTerapiRepository = sesiTerapiContainer.sesiTerapiRepository
    override val jenisTerapiRepository = jenisTerapiContainer.jenisTerapiRepository
    override val terapisRepository = terapisContainer.terapisRepository
    override val pasienRepository = pasienContainer.pasienRepository
}