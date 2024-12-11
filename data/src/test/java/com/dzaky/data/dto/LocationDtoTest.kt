package com.dzaky.data.dto

import com.dzaky.core_ui.util.orEmpty
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationDtoTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `should deserialize location response correctly`() {
        // Given
        val jsonString = """
            {
                "id": 1,
                "name": "London",
                "lat": 51.5074,
                "lon": -0.1278,
                "country": "United Kingdom"
            }
        """.trimIndent()

        // When
        val locationDto = json.decodeFromString<LocationDto>(jsonString)

        // Then
        assertEquals(1L, locationDto.id)
        assertEquals("London", locationDto.name)
        assertEquals(51.5074, locationDto.lat.orEmpty(), 0.0001)
        assertEquals(-0.1278, locationDto.lon.orEmpty(), 0.0001)
    }
}