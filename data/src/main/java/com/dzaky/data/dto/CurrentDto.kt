package com.dzaky.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDto(
    @SerialName("temp_c") val tempInCelsius: Double? = null,
    val condition: ConditionDto? = null,
    val humidity: Int? = null,
    @SerialName("feelslike_c") val feelsLikeInCelsius: Double? = null,
    val uv: Double? = null
)
