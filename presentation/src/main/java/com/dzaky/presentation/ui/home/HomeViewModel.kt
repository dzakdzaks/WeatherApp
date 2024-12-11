@file:OptIn(FlowPreview::class)

package com.dzaky.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzaky.core_ui.model.onError
import com.dzaky.core_ui.model.onSuccess
import com.dzaky.core_ui.util.toUiText
import com.dzaky.domain.usecase.GetCoordinatesUseCase
import com.dzaky.domain.usecase.GetCurrentWeatherUseCase
import com.dzaky.domain.usecase.SaveCoordinatesUseCase
import com.dzaky.domain.usecase.SearchLocationUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import android.util.Log

class HomeViewModel(
    private val searchLocationUseCase: SearchLocationUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val saveCoordinatesUseCase: SaveCoordinatesUseCase,
    private val getCoordinatesUseCase: GetCoordinatesUseCase
) : ViewModel() {

    private var searchJob: Job? = null
    private var getCurrentJob: Job? = null

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            observeSearchQuery()
            observeCoordinates()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnSearchLocation -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is HomeAction.OnSelectedLocationChange -> {
                val coordinates = action.weather.lat to action.weather.lon
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            weather = action.weather,
                            coordinates = coordinates,
                            searchQuery = "",
                            searchResults = emptyList()
                        )
                    }

                    saveCoordinatesUseCase(coordinates.first, coordinates.second)
                }
            }

            HomeAction.OnRetryWeather -> {
                if (getCurrentJob?.isActive?.not() == true) {
                    getCurrentWeather()
                }
            }
        }
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                searchResults = emptyList(),
                                isAfterSearch = false
                            )
                        }
                    }

                    query.length >= 3 -> {
                        searchJob?.cancel()
                        searchJob = searchLocations(query)
                    }

                    else -> {
                        logErrorMessage("Clearing errorMessage due to short query (< 3 characters)")
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = emptyList(),
                                isAfterSearch = false
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchLocations(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        searchLocationUseCase(query)
            .onSuccess { searchResults ->
                logErrorMessage("Clearing errorMessage after successful search")
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults,
                        isAfterSearch = true
                    )
                }
            }
            .onError { error ->
                val uiText = error.toUiText()
                logErrorMessage("Setting errorMessage: $uiText")
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = uiText
                    )
                }
            }
    }

    private fun getCurrentWeather() {
        getCurrentJob?.cancel()
        getCurrentJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            getCurrentWeatherUseCase(state.value.coordinates.first, state.value.coordinates.second)
                .onSuccess { weather ->
                    logErrorMessage("Clearing errorMessage after successful weather fetch")
                    _state.update {
                        it.copy(
                            searchResults = emptyList(),
                            weather = weather,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onError { error ->
                    val uiText = error.toUiText()
                    logErrorMessage("Setting errorMessage: $uiText")
                    _state.update {
                        it.copy(
                            searchResults = emptyList(),
                            weather = null,
                            isLoading = false,
                            errorMessage = uiText
                        )
                    }
                }
        }
    }

    private fun observeCoordinates() {
        getCoordinatesUseCase()
            .onEach { coordinates ->
                val (lat, lon) = coordinates
                _state.update {
                    it.copy(
                        coordinates = coordinates,
                        isLoading = false
                    )
                }

                if (lat != 0.0 && lon != 0.0 && state.value.weather == null) {
                    getCurrentWeather()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun logErrorMessage(message: String) {
        Log.d("HomeViewModel", message)
    }
}
