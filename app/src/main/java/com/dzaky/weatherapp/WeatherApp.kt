package com.dzaky.weatherapp

import android.app.Application
import com.dzaky.data.di.dataModule
import com.dzaky.domain.di.domainModule
import com.dzaky.presentation.di.presentationModule
import com.dzaky.weatherapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(appModule, domainModule, dataModule , presentationModule)
        }
    }
}