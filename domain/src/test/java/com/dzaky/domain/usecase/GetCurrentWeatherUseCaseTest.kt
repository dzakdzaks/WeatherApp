@file:OptIn(ExperimentalCoroutinesApi::class)

package com.dzaky.domain.usecase

import com.dzaky.core_ui.model.DataError
import com.dzaky.core_ui.model.WeResult
import com.dzaky.core_ui.util.WeDispatchers
import com.dzaky.domain.model.Weather
import com.dzaky.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrentWeatherUseCaseTest {
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var dispatchers: WeDispatchers
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Before
    fun setup() {
        weatherRepository = mockk()
        testDispatcher = UnconfinedTestDispatcher()
        dispatchers = WeDispatchers(
            io = testDispatcher,
            main = testDispatcher
        )
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository, dispatchers)
    }

    @Test
    fun `when invoke succeeds should return weather`() = runTest {
        // Given
        val lat = 51.5074
        val lon = -0.1278
        val expectedWeather = Weather(
            id = 1,
            name = "London",
            lat = lat,
            lon = lon,
            tempInCelsius = 20.0,
            icon = "sunny.png",
            humidity = 70,
            feelsLikeInCelsius = 21.0,
            uv = 4.0
        )
        coEvery { weatherRepository.getCurrentWeather(lat, lon) } returns WeResult.Success(expectedWeather)

        // When
        val result = getCurrentWeatherUseCase(lat, lon)

        // Then
        assert(result is WeResult.Success)
        assertEquals(expectedWeather, (result as WeResult.Success).data)
    }

    @Test
    fun `when invoke fails should return error`() = runTest {
        // Given
        val lat = 51.5074
        val lon = -0.1278
        coEvery { weatherRepository.getCurrentWeather(lat, lon) } returns WeResult.Error(DataError.Remote.NO_INTERNET)

        // When
        val result = getCurrentWeatherUseCase(lat, lon)

        // Then
        assert(result is WeResult.Error)
        assertEquals(DataError.Remote.NO_INTERNET, (result as WeResult.Error).error)
    }
}