package com.dzaky.data.repository

import com.dzaky.core_ui.model.DataError
import com.dzaky.core_ui.model.WeResult
import com.dzaky.core_ui.preference.PreferencesStorage
import com.dzaky.data.dto.ConditionDto
import com.dzaky.data.dto.CurrentDto
import com.dzaky.data.dto.LocationDto
import com.dzaky.data.dto.WeatherDto
import com.dzaky.data.network.RemoteWeatherDataSource
import com.dzaky.domain.model.Weather
import com.dzaky.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {
    private lateinit var remoteDataSource: RemoteWeatherDataSource
    private lateinit var preferencesStorage: PreferencesStorage
    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        remoteDataSource = mockk()
        preferencesStorage = mockk()
        repository = WeatherRepositoryImpl(remoteDataSource, preferencesStorage)
    }

    @Test
    fun `searchLocation when successful should return weather list`() = runTest {
        // Given
        val query = "London"
        val expectedLocationDto = listOf(
            LocationDto(
                id = 1,
                name = "London",
                lat = 51.5074,
                lon = -0.1278
            )
        )
        val expectedWeather = listOf(
            Weather(
                id = 1,
                name = "London",
                lat = 51.5074,
                lon = -0.1278
            )
        )
        coEvery { remoteDataSource.searchLocation(query) } returns WeResult.Success(expectedLocationDto)

        // When
        val result = repository.searchLocation(query)

        // Then
        assert(result is WeResult.Success)
        assertEquals(expectedWeather, (result as WeResult.Success).data)
    }

    @Test
    fun `searchLocation when fails should return error`() = runTest {
        // Given
        val query = "London"
        coEvery { remoteDataSource.searchLocation(query) } returns WeResult.Error(DataError.Remote.NO_INTERNET)

        // When
        val result = repository.searchLocation(query)

        // Then
        assert(result is WeResult.Error)
        assertEquals(DataError.Remote.NO_INTERNET, (result as WeResult.Error).error)
    }

    @Test
    fun `getCurrentWeather when successful should return weather`() = runTest {
        // Given
        val lat = 51.5074
        val lon = -0.1278

        val expectedWeatherDto = WeatherDto(
            location = LocationDto(
                id = 1,
                name = "London",
                lat = lat,
                lon = lon,
            ),
            current = CurrentDto(
                tempInCelsius = 20.0,
                condition = ConditionDto(
                    icon = "//cdn.weatherapi.com/weather/64x64/day/116.png"
                ),
                humidity = 70,
                feelsLikeInCelsius = 21.0,
                uv = 4.0
            ),
        )

        val expectedWeather = Weather(
            id = 1,
            name = "London",
            lat = lat,
            lon = lon,
            tempInCelsius = 20.0,
            icon = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
            humidity = 70,
            feelsLikeInCelsius = 21.0,
            uv = 4.0
        )
        coEvery { remoteDataSource.getCurrentWeather(lat, lon) } returns WeResult.Success(expectedWeatherDto)

        // When
        val result = repository.getCurrentWeather(lat, lon)

        // Then
        assert(result is WeResult.Success)
        assertEquals(expectedWeather, (result as WeResult.Success).data)
    }

    @Test
    fun `getCurrentWeather when fails should return error`() = runTest {
        // Given
        val lat = 51.5074
        val lon = -0.1278
        coEvery { remoteDataSource.getCurrentWeather(lat, lon) } returns WeResult.Error(DataError.Remote.NO_INTERNET)

        // When
        val result = repository.getCurrentWeather(lat, lon)

        // Then
        assert(result is WeResult.Error)
        assertEquals(DataError.Remote.NO_INTERNET, (result as WeResult.Error).error)
    }

    @Test
    fun `saveCoordinates should save to preferences`() = runTest {
        // Given
        val lat = 51.5074
        val lon = -0.1278
        coEvery { preferencesStorage.saveCoordinates(lat, lon) } returns Unit

        // When
        repository.saveCoordinates(lat, lon)

        // Then
        coVerify { preferencesStorage.saveCoordinates(lat, lon) }
    }

    @Test
    fun `getCoordinates should return flow from preferences`() = runTest {
        // Given
        val coordinates = Pair(51.5074, -0.1278)
        every { preferencesStorage.getCoordinates() } returns flowOf(coordinates)

        // When
        val result = repository.getCoordinates()

        // Then
        assertEquals(coordinates, result.first())
    }
}