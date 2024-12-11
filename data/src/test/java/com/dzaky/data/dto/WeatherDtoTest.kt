package com.dzaky.data.dto

import com.dzaky.core_ui.util.orEmpty
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherDtoTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `should deserialize weather response correctly`() {
        // Given
        val jsonString = """
            {
                "location": {
                    "name": "London",
                    "lat": 51.5074,
                    "lon": -0.1278,
                    "country": "United Kingdom"
                },
                "current": {
                    "temp_c": 20.0,
                    "condition": {
                        "text": "Partly cloudy",
                        "icon": "//cdn.weatherapi.com/weather/64x64/day/116.png"
                    },
                    "humidity": 70,
                    "feelslike_c": 21.0,
                    "uv": 4.0
                }
            }
        """.trimIndent()

        // When
        val weatherDto = json.decodeFromString<WeatherDto>(jsonString)

        // Then
        assertEquals("London", weatherDto.location?.name.orEmpty())
        assertEquals(51.5074, weatherDto.location?.lat.orEmpty(), 0.0001)
        assertEquals(-0.1278, weatherDto.location?.lon.orEmpty(), 0.0001)
        assertEquals(20.0, weatherDto.current?.tempInCelsius.orEmpty(), 0.0001)
        assertEquals("//cdn.weatherapi.com/weather/64x64/day/116.png", weatherDto.current?.condition?.icon)
        assertEquals(70, weatherDto.current?.humidity.orEmpty())
        assertEquals(21.0, weatherDto.current?.feelsLikeInCelsius.orEmpty(), 0.0001)
        assertEquals(4.0, weatherDto.current?.uv.orEmpty(), 0.0001)
    }
}