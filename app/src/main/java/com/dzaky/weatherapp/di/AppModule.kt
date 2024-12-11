package com.dzaky.weatherapp.di

import com.dzaky.core_ui.http.HttpClientFactory
import com.dzaky.core_ui.preference.PreferencesDataStoreImpl
import com.dzaky.core_ui.preference.PreferencesStorage
import com.dzaky.core_ui.util.WeDispatchers
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { WeDispatchers() }
    single<HttpClientEngine> { OkHttp.create() }
    single<HttpClient> { HttpClientFactory.create(get()) }
    singleOf(::PreferencesDataStoreImpl).bind<PreferencesStorage>()
}