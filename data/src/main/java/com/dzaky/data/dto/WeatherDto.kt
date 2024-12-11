package com.dzaky.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val location: LocationDto? = null,
    val current: CurrentDto? = null
)