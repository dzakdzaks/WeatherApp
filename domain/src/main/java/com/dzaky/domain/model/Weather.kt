package com.dzaky.domain.model

data class Weather(
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val tempInCelsius: Double = 0.0,
    val icon: String = "",
    val humidity: Int = 0,
    val feelsLikeInCelsius: Double = 0.0,
    val uv: Double = 0.0
)
