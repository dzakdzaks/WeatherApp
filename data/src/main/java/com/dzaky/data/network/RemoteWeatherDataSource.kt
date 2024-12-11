package com.dzaky.data.network

import com.dzaky.core_ui.model.DataError
import com.dzaky.core_ui.model.WeResult
import com.dzaky.data.dto.LocationDto
import com.dzaky.data.dto.WeatherDto

interface RemoteWeatherDataSource {
    suspend fun searchLocation(
        query: String,
    ): WeResult<List<LocationDto>, DataError.Remote>

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeResult<WeatherDto, DataError.Remote>
}