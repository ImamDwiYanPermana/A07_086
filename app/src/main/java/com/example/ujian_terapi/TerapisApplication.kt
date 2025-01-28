package com.example.ujian_terapi

import android.app.Application
import com.example.ujian_terapi.dependenciesinjection.AppContainer
import com.example.ujian_terapi.dependenciesinjection.MainAppContainer
import com.example.ujian_terapi.dependenciesinjection.PasienContainer

class TerapisApplication:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MainAppContainer()
    }
}