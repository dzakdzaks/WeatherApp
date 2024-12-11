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

class SearchLocationUseCaseTest {
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var dispatchers: WeDispatchers
    private lateinit var searchLocationUseCase: SearchLocationUseCase

    @Before
    fun setup() {
        weatherRepository = mockk()
        testDispatcher = UnconfinedTestDispatcher()
        dispatchers = WeDispatchers(
            io = testDispatcher,
            main = testDispatcher
        )
        searchLocationUseCase = SearchLocationUseCase(weatherRepository, dispatchers)
    }

    @Test
    fun `when search location success should return weather list`() = runTest {
        // Given
        val query = "London"
        val weatherList = listOf(
            Weather(
                id = 1,
                name = "London",
                lat = 51.5074,
                lon = -0.1278
            )
        )
        val currentWeather = Weather(
            id = 1,
            name = "London",
            lat = 51.5074,
            lon = -0.1278,
            tempInCelsius = 20.0,
            icon = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
            humidity = 70,
            feelsLikeInCelsius = 21.0,
            uv = 4.0
        )

        coEvery { weatherRepository.searchLocation(query) } returns WeResult.Success(weatherList)
        coEvery { weatherRepository.getCurrentWeather(any(), any()) } returns WeResult.Success(currentWeather)

        // When
        val result = searchLocationUseCase(query)

        // Then
        assert(result is WeResult.Success)
        assertEquals((result as WeResult.Success).data.first(), currentWeather)
    }

    @Test
    fun `when search location fails should return error`() = runTest {
        // Given
        val query = "London"
        coEvery { weatherRepository.searchLocation(query) } returns WeResult.Error(DataError.Remote.NO_INTERNET)

        // When
        val result = searchLocationUseCase(query)

        // Then
        assert(result is WeResult.Error)
        assertEquals((result as WeResult.Error).error, DataError.Remote.NO_INTERNET)
    }
}