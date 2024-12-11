package com.dzaky.presentation.ui.home

import com.dzaky.domain.model.Weather

sealed interface HomeAction {
    data class OnSearchLocation(val query: String) : HomeAction
    data class OnSelectedLocationChange(val weather: Weather) : HomeAction
    data object OnRetryWeather: HomeAction
}