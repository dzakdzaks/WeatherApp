package com.dzaky.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherTest {
    @Test
    fun `weather data class should have correct values`() {
        // Given
        val id = 1L
        val name = "London"
        val lat = 51.5074
        val lon = -0.1278
        val tempInCelsius = 20.0
        val icon = "sunny.png"
        val humidity = 70
        val feelsLike = 21.0
        val uv = 4.0

        // When
        val weather = Weather(
            id = id,
            name = name,
            lat = lat,
            lon = lon,
            tempInCelsius = tempInCelsius,
            icon = icon,
            humidity = humidity,
            feelsLikeInCelsius = feelsLike,
            uv = uv
        )

        // Then
        assertEquals(id, weather.id)
        assertEquals(name, weather.name)
        assertEquals(lat, weather.lat, 0.0001)
        assertEquals(lon, weather.lon, 0.0001)
        assertEquals(tempInCelsius, weather.tempInCelsius, 0.1)
        assertEquals(icon, weather.icon)
        assertEquals(humidity, weather.humidity)
        assertEquals(feelsLike, weather.feelsLikeInCelsius, 0.1)
        assertEquals(uv, weather.uv, 0.1)
    }

    @Test
    fun `weather copy should create new instance with modified values`() {
        // Given
        val original = Weather(
            id = 1,
            name = "London",
            lat = 51.5074,
            lon = -0.1278
        )

        // When
        val copied = original.copy(name = "Paris")

        // Then
        assertEquals("Paris", copied.name)
        assertEquals(original.id, copied.id)
        assertEquals(original.lat, copied.lat, 0.0001)
        assertEquals(original.lon, copied.lon, 0.0001)
    }
}