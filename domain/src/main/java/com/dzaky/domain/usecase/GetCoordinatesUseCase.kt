package com.dzaky.domain.usecase

import com.dzaky.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetCoordinatesUseCase(private val weatherRepository: WeatherRepository) {
    operator fun invoke(): Flow<Pair<Double, Double>> {
        return weatherRepository.getCoordinates()
    }
}