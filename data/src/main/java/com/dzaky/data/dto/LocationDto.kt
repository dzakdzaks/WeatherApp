package com.dzaky.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Long? = null,
    val name: String? = null,
    val lat: Double? = null,
    val lon: Double? = null
)