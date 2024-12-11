package com.dzaky.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dzaky.core_ui.util.NetworkConnectionState
import com.dzaky.domain.model.Weather
import com.dzaky.presentation.ui.component.ErrorMessage
import com.dzaky.presentation.ui.component.HomeSearchBar
import com.dzaky.presentation.ui.component.Loading
import com.dzaky.presentation.ui.component.LocationList
import com.dzaky.presentation.ui.component.NoCitySelectedMessage
import com.dzaky.presentation.ui.component.NoLocationFoundMessage
import com.dzaky.presentation.ui.component.WeatherView
import com.dzaky.presentation.util.rememberConnectivityState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(state = state, onAction = viewModel::onAction)
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val connectionState by rememberConnectivityState()

    val isConnected by remember(connectionState) {
        derivedStateOf {
            connectionState === NetworkConnectionState.Available
        }
    }

    LaunchedEffect(isConnected) {
        if (isConnected) {
            onAction(HomeAction.OnRetryWeather)
        }
    }

    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val locationListState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeSearchBar(
            modifier = Modifier
                .padding(
                    top = 44.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .focusRequester(focusRequester),
            searchQuery = state.searchQuery,
            onSearchQueryChange = { query ->
                onAction(HomeAction.OnSearchLocation(query = query))
            },
            onImeSearch = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                state.isLoading -> Loading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = 200.dp,
                            start = 24.dp,
                            end = 24.dp
                        )
                )

                state.errorMessage != null -> {
                    val errorMessage = state.errorMessage.asString(context)
                    ErrorMessage(errorMessage)
                }
                state.isAfterSearch -> HandleAfterSearchState(
                    state = state,
                    listState = locationListState,
                    onClick = { weather ->
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onAction(HomeAction.OnSelectedLocationChange(weather = weather))
                    }
                )

                else -> {
                    Log.d("walwaw", "1")
                    HandleWeatherState(state = state)
                }
            }
        }
    }
}

@Composable
private fun HandleAfterSearchState(
    state: HomeState,
    listState: LazyListState,
    onClick: (Weather) -> Unit
) {
    when {
        state.searchQuery.isNotBlank() && state.searchResults.isEmpty() -> NoLocationFoundMessage()
        state.searchQuery.isNotBlank() && state.searchResults.isNotEmpty() -> LocationList(
            listState = listState,
            items = state.searchResults,
            onClick = onClick
        )
    }
}

@Composable
private fun HandleWeatherState(state: HomeState) {
    if (state.weather == null) {
        NoCitySelectedMessage()
    } else {
        WeatherView(
            modifier = Modifier.fillMaxSize().padding(bottom = 200.dp),
            weather = state.weather
        )
    }
}