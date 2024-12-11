package com.dzaky.data.network

import com.dzaky.core_ui.http.safeCall
import com.dzaky.core_ui.model.DataError
import com.dzaky.core_ui.model.WeResult
import com.dzaky.data.BuildConfig
import com.dzaky.data.dto.LocationDto
import com.dzaky.data.dto.WeatherDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorRemoteWeatherDataSource(
    private val httpClient: HttpClient
): RemoteWeatherDataSource {

    private val baseUrl = BuildConfig.BASE_URL
    private val key = BuildConfig.KEY

    companion object {
        private const val PARAM_KEY = "key"
        private const val PARAM_Q = "q"
    }

    override suspend fun searchLocation(query: String): WeResult<List<LocationDto>, DataError.Remote> {
        return safeCall<List<LocationDto>> {
            httpClient.get(
                urlString = "$baseUrl/search.json"
            ) {
                parameter(PARAM_KEY, key)
                parameter(PARAM_Q, query)
            }
        }
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeResult<WeatherDto, DataError.Remote> {
       return safeCall<WeatherDto> {
           httpClient.get(
               urlString = "$baseUrl/current.json"
           ) {
               parameter(PARAM_KEY, key)
               parameter(PARAM_Q, "$lat,$lon")
           }
       }
    }

}