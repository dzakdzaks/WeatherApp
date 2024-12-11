package com.dzaky.data.dto

import com.dzaky.core_ui.util.orEmpty
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrentDtoTest {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Test
    fun `should deserialize current weather data correctly`() {
        // Given
        val jsonString = """
            {
                "temp_c": 20.0,
                "condition": {
                    "icon": "//cdn.weatherapi.com/weather/64x64/day/116.png"
                },
                "humidity": 70,
                "feelslike_c": 21.0,
                "uv": 4.0
            }
        """.trimIndent()

        // When
        val currentDto = json.decodeFromString<CurrentDto>(jsonString)

        // Then
        assertEquals(20.0, currentDto.tempInCelsius.orEmpty(), 0.1)
        assertEquals(70, currentDto.humidity)
        assertEquals(21.0, currentDto.feelsLikeInCelsius.orEmpty(), 0.1)
        assertEquals(4.0, currentDto.uv.orEmpty(), 0.1)
        assertEquals("//cdn.weatherapi.com/weather/64x64/day/116.png", currentDto.condition?.icon.orEmpty())
    }

    @Test
    fun `should handle missing optional fields`() {
        // Given
        val jsonString = """
            {
                "temp_c": 20.0,
                "condition": {
                    "icon": "//cdn.weatherapi.com/weather/64x64/day/116.png"
                }
            }
        """.trimIndent()

        // When
        val currentDto = json.decodeFromString<CurrentDto>(jsonString)

        // Then
        assertEquals(20.0, currentDto.tempInCelsius.orEmpty(), 0.1)
        assertEquals(0, currentDto.humidity.orEmpty()) // Default value
        assertEquals(0.0, currentDto.feelsLikeInCelsius.orEmpty(), 0.1) // Default value
        assertEquals(0.0, currentDto.uv.orEmpty(), 0.1) // Default value
        assertEquals("//cdn.weatherapi.com/weather/64x64/day/116.png", currentDto.condition?.icon.orEmpty())
    }
}