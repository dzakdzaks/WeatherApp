package com.dzaky.domain.usecase

import com.dzaky.domain.repository.WeatherRepository

class SaveCoordinatesUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double) {
        weatherRepository.saveCoordinates(lat, lon)
    }
}
