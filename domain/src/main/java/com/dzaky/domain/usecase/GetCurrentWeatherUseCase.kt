package com.dzaky.domain.usecase

import com.dzaky.core_ui.model.DataError
import com.dzaky.core_ui.model.WeResult
import com.dzaky.core_ui.util.WeDispatchers
import com.dzaky.domain.model.Weather
import com.dzaky.domain.repository.WeatherRepository
import kotlinx.coroutines.withContext

class GetCurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatchers: WeDispatchers
) {
    suspend operator fun invoke(lat: Double, lon: Double): WeResult<Weather, DataError.Remote> = withContext(dispatchers.io) {
        weatherRepository.getCurrentWeather(lat, lon)
    }
}
