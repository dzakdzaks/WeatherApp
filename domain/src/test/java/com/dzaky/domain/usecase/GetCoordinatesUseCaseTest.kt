package com.dzaky.domain.usecase

import com.dzaky.domain.repository.WeatherRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCoordinatesUseCaseTest {
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getCoordinatesUseCase: GetCoordinatesUseCase

    @Before
    fun setup() {
        weatherRepository = mockk()
        getCoordinatesUseCase = GetCoordinatesUseCase(weatherRepository)
    }

    @Test
    fun `when get coordinates should return coordinates flow`() = runTest {
        // Given
        val coordinates = Pair(51.5074, -0.1278)
        every { weatherRepository.getCoordinates() } returns flowOf(coordinates)

        // When
        val result = getCoordinatesUseCase()

        // Then
        assertEquals(coordinates, result.first())
    }
}