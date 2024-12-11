package com.dzaky.domain.repository

import com.dzaky.core_ui.model.DataError
import com.dzaky.core_ui.model.WeResult
import com.dzaky.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun searchLocation(query: String): WeResult<List<Weather>, DataError.Remote>
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeResult<Weather, DataError.Remote>
    suspend fun saveCoordinates(lat: Double, lon: Double)
    fun getCoordinates(): Flow<Pair<Double, Double>>
}