package com.dzaky.data.mapper

import com.dzaky.core_ui.util.appendHttps
import com.dzaky.core_ui.util.orEmpty
import com.dzaky.data.dto.LocationDto
import com.dzaky.data.dto.WeatherDto
import com.dzaky.domain.model.Weather

fun LocationDto.toWeather(): Weather {
    return Weather(
        id = id.orEmpty(),
        name = name.orEmpty(),
        lat = lat.orEmpty(),
        lon = lon.orEmpty(),
    )
}

fun WeatherDto.toWeather(): Weather {
    return Weather(
        id = location?.id.orEmpty(),
        name = location?.name.orEmpty(),
        lat = location?.lat.orEmpty(),
        lon = location?.lon.orEmpty(),
        tempInCelsius = current?.tempInCelsius.orEmpty(),
        icon = current?.condition?.icon.orEmpty().appendHttps(),
        humidity = current?.humidity.orEmpty(),
        feelsLikeInCelsius = current?.feelsLikeInCelsius.orEmpty(),
        uv = current?.uv.orEmpty()
    )
}