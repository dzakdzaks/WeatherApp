package com.dzaky.domain.usecase

import com.dzaky.core_ui.model.DataError
import com.dzaky.core_ui.model.WeResult
import com.dzaky.core_ui.model.map
import com.dzaky.core_ui.util.WeDispatchers
import com.dzaky.core_ui.util.appendHttps
import com.dzaky.domain.model.Weather
import com.dzaky.domain.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SearchLocationUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatchers: WeDispatchers
) {
    suspend operator fun invoke(query: String): WeResult<List<Weather>, DataError.Remote> = withContext(dispatchers.io) {
        val weatherLocationResult = weatherRepository.searchLocation(query)

        weatherLocationResult.map { weatherLocations ->
            // Process each location concurrently
            weatherLocations.map { weatherLocation ->
                async {
                    val lat = weatherLocation.lat
                    val lon = weatherLocation.lon

                    when (val weatherResult = weatherRepository.getCurrentWeather(lat, lon)) {
                        is WeResult.Success -> weatherLocation.copy(
                            tempInCelsius = weatherResult.data.tempInCelsius,
                            icon = weatherResult.data.icon.appendHttps(),
                            humidity = weatherResult.data.humidity,
                            feelsLikeInCelsius = weatherResult.data.feelsLikeInCelsius,
                            uv = weatherResult.data.uv
                        )
                        is WeResult.Error -> weatherLocation // Return the existing data on failure
                    }
                }
            }.map { it.await() } // Await all tasks
        }
    }
}
