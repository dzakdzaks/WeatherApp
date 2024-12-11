package com.dzaky.domain.di

import com.dzaky.domain.usecase.SearchLocationUseCase
import com.dzaky.domain.usecase.GetCurrentWeatherUseCase
import com.dzaky.domain.usecase.SaveCoordinatesUseCase
import com.dzaky.domain.usecase.GetCoordinatesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::SearchLocationUseCase)
    factoryOf(::GetCurrentWeatherUseCase)
    factoryOf(::SaveCoordinatesUseCase)
    factoryOf(::GetCoordinatesUseCase)
}