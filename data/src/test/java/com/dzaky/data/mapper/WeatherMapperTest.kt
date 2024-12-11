package com.dzaky.data.mapper

import com.dzaky.data.dto.ConditionDto
import com.dzaky.data.dto.CurrentDto
import com.dzaky.data.dto.LocationDto
import com.dzaky.data.dto.WeatherDto
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherMapperTest {
    @Test
    fun `should map WeatherDto to Weather domain model correctly`() {
        // Given
        val weatherDto = WeatherDto(
            location = LocationDto(
                id = 1L,
                name = "London",
                lat = 51.5074,
                lon = -0.1278,
            ),
            current = CurrentDto(
                tempInCelsius = 20.0,
                condition = ConditionDto(
                    icon = "//cdn.weatherapi.com/weather/64x64/day/116.png"
                ),
                humidity = 70,
                feelsLikeInCelsius = 21.0,
                uv = 4.0
            )
        )

        // When
        val weather = weatherDto.toWeather()

        // Then
        assertEquals(1L, weather.id)
        assertEquals("London", weather.name)
        assertEquals(51.5074, weather.lat, 0.0001)
        assertEquals(-0.1278, weather.lon, 0.0001)
        assertEquals(20.0, weather.tempInCelsius, 0.1)
        assertEquals("https://cdn.weatherapi.com/weather/64x64/day/116.png", weather.icon)
        assertEquals(70, weather.humidity)
        assertEquals(21.0, weather.feelsLikeInCelsius, 0.1)
        assertEquals(4.0, weather.uv, 0.1)
    }

    @Test
    fun `should map LocationDto to Weather domain model correctly`() {
        // Given
        val locationDto = LocationDto(
            id = 1L,
            name = "London",
            lat = 51.5074,
            lon = -0.1278,
        )

        // When
        val weather = locationDto.toWeather()

        // Then
        assertEquals(1L, weather.id)
        assertEquals("London", weather.name)
        assertEquals(51.5074, weather.lat, 0.0001)
        assertEquals(-0.1278, weather.lon, 0.0001)
        // Other fields should be null or default values since this is a basic location mapping
        assertEquals(0.0, weather.tempInCelsius, 0.1)
        assertEquals("", weather.icon)
        assertEquals(0, weather.humidity)
        assertEquals(0.0, weather.feelsLikeInCelsius, 0.1)
        assertEquals(0.0, weather.uv, 0.1)
    }
}