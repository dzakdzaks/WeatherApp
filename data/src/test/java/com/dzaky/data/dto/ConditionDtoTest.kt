package com.dzaky.data.dto

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class ConditionDtoTest {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Test
    fun `should deserialize condition data correctly`() {
        // Given
        val jsonString = """
            {
                "icon": "//cdn.weatherapi.com/weather/64x64/day/116.png"
            }
        """.trimIndent()

        // When
        val conditionDto = json.decodeFromString<ConditionDto>(jsonString)

        // Then
        assertEquals("//cdn.weatherapi.com/weather/64x64/day/116.png", conditionDto.icon)
    }

    @Test
    fun `should handle missing text field`() {
        // Given
        val jsonString = """
            {
                "icon": "//cdn.weatherapi.com/weather/64x64/day/116.png"
            }
        """.trimIndent()

        // When
        val conditionDto = json.decodeFromString<ConditionDto>(jsonString)

        // Then
        assertEquals("//cdn.weatherapi.com/weather/64x64/day/116.png", conditionDto.icon)
    }

    @Test
    fun `should handle missing icon field`() {
        // Given
        val jsonString = """
            {
                "text": "Partly cloudy"
            }
        """.trimIndent()

        // When
        val conditionDto = json.decodeFromString<ConditionDto>(jsonString)

        // Then
        assertEquals("", conditionDto.icon) // Default value
    }

    @Test
    fun `should handle empty object`() {
        // Given
        val jsonString = "{}"

        // When
        val conditionDto = json.decodeFromString<ConditionDto>(jsonString)

        // Then
        assertEquals("", conditionDto.icon) // Default value
    }
}