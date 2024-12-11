package com.dzaky.presentation.ui.home

import com.dzaky.core_ui.util.UiText
import com.dzaky.domain.model.Weather

data class HomeState(
    val isLoading: Boolean = true,
    val isAfterSearch: Boolean = false,
    val searchQuery: String = "",
    val errorMessage: UiText? = null,
    val searchResults: List<Weather> = emptyList(),
    val coordinates: Pair<Double, Double> = 0.0 to 0.0,
    val weather: Weather? = null
)