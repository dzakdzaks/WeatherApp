package com.dzaky.data.repository

import com.dzaky.core_ui.model.DataError
import com.dzaky.core_ui.model.WeResult
import com.dzaky.core_ui.model.map
import com.dzaky.core_ui.preference.PreferencesStorage
import com.dzaky.data.mapper.toWeather
import com.dzaky.data.network.RemoteWeatherDataSource
import com.dzaky.domain.model.Weather
import com.dzaky.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImpl(
    private val remoteWeatherDataSource: RemoteWeatherDataSource,
    private val preferencesStorage: PreferencesStorage
): WeatherRepository {
    override suspend fun searchLocation(query: String): WeResult<List<Weather>, DataError.Remote> {
        return remoteWeatherDataSource
            .searchLocation(query)
            .map { locations ->
                locations.map { it.toWeather() }
            }
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeResult<Weather, DataError.Remote> {
        return remoteWeatherDataSource
            .getCurrentWeather(lat, lon)
            .map { it.toWeather() }
    }

    override suspend fun saveCoordinates(lat: Double, lon: Double) {
        preferencesStorage.saveCoordinates(lat, lon)
    }

    override fun getCoordinates(): Flow<Pair<Double, Double>> {
        return preferencesStorage.getCoordinates()
    }
}