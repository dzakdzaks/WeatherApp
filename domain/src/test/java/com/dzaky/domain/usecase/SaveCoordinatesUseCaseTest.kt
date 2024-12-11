package com.dzaky.domain.usecase

import com.dzaky.domain.repository.WeatherRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveCoordinatesUseCaseTest {
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var saveCoordinatesUseCase: SaveCoordinatesUseCase

    @Before
    fun setup() {
        weatherRepository = mockk(relaxed = true)
        saveCoordinatesUseCase = SaveCoordinatesUseCase(weatherRepository)
    }

    @Test
    fun `when save coordinates should call repository`() = runTest {
        // Given
        val lat = 51.5074
        val lon = -0.1278

        // When
        saveCoordinatesUseCase(lat, lon)

        // Then
        coVerify { weatherRepository.saveCoordinates(lat, lon) }
    }
}