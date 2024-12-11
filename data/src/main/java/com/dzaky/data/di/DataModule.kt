package com.dzaky.data.di

import com.dzaky.data.network.KtorRemoteWeatherDataSource
import com.dzaky.data.network.RemoteWeatherDataSource
import com.dzaky.data.repository.WeatherRepositoryImpl
import com.dzaky.domain.repository.WeatherRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::KtorRemoteWeatherDataSource).bind<RemoteWeatherDataSource>()
    singleOf(::WeatherRepositoryImpl).bind<WeatherRepository>()
}
