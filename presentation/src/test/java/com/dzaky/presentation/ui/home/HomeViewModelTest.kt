package com.dzaky.presentation.ui.home

import android.util.Log
import app.cash.turbine.test
import com.dzaky.core_ui.model.WeResult
import com.dzaky.domain.model.Weather
import com.dzaky.domain.usecase.GetCoordinatesUseCase
import com.dzaky.domain.usecase.GetCurrentWeatherUseCase
import com.dzaky.domain.usecase.SaveCoordinatesUseCase
import com.dzaky.domain.usecase.SearchLocationUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var searchLocationUseCase: SearchLocationUseCase
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    private lateinit var saveCoordinatesUseCase: SaveCoordinatesUseCase
    private lateinit var getCoordinatesUseCase: GetCoordinatesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchLocationUseCase = mockk()
        getCurrentWeatherUseCase = mockk()
        saveCoordinatesUseCase = mockk()
        getCoordinatesUseCase = mockk()

        // Mock Android Log
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        // Default mock for coordinates
        coEvery { getCoordinatesUseCase() } returns flowOf(0.0 to 0.0)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() {
        viewModel = HomeViewModel(
            searchLocationUseCase,
            getCurrentWeatherUseCase,
            saveCoordinatesUseCase,
            getCoordinatesUseCase
        )
    }

    @Test
    fun `when search query is blank, clear search results`() = runTest {
        createViewModel()

        viewModel.state.test {
            val initial = awaitItem()
            assertEquals(emptyList(), initial.searchResults)

            viewModel.onAction(HomeAction.OnSearchLocation(""))
            advanceTimeBy(600L) // Account for debounce

            val updated = expectMostRecentItem()
            assertEquals(emptyList(), updated.searchResults)
            assertEquals(false, updated.isAfterSearch)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when search query is less than 3 characters, clear error message`() = runTest {
        createViewModel()

        viewModel.state.test {
            val initial = awaitItem()
            assertNull(initial.errorMessage)

            viewModel.onAction(HomeAction.OnSearchLocation("ab"))
            advanceTimeBy(600L) // Account for debounce

            val updated = expectMostRecentItem()
            assertNull(updated.errorMessage)
            assertEquals(emptyList(), updated.searchResults)
            assertEquals(false, updated.isAfterSearch)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when search location succeeds, update state with results`() = runTest {
        val mockWeather = Weather(
            id = 1,
            name = "London",
            lat = 51.5074,
            lon = -0.1278,
            tempInCelsius = 20.0,
            feelsLikeInCelsius = 19.0,
            humidity = 80,
            icon = "04d"
        )
        val searchResults = listOf(mockWeather)

        coEvery { searchLocationUseCase(any()) } returns WeResult.Success(searchResults)

        createViewModel()

        viewModel.state.test {
            val initial = awaitItem()

            viewModel.onAction(HomeAction.OnSearchLocation("London"))
            advanceTimeBy(600L) // Account for debounce

            val updated = expectMostRecentItem()
            assertEquals(searchResults, updated.searchResults)
            assertEquals(false, updated.isLoading)
            assertNull(updated.errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when location is selected, save coordinates and update state`() = runTest {
        val mockWeather = Weather(
            id = 1,
            name = "London",
            lat = 51.5074,
            lon = -0.1278,
            tempInCelsius = 20.0,
            feelsLikeInCelsius = 19.0,
            humidity = 80,
            icon = "04d"
        )

        coEvery { saveCoordinatesUseCase(any(), any()) } returns Unit

        createViewModel()

        viewModel.state.test {
            val initial = awaitItem() // Get initial state

            viewModel.onAction(HomeAction.OnSelectedLocationChange(mockWeather))
            advanceTimeBy(100L) // Advance time to allow state updates

            val updated = awaitItem() // Get updated state
            assertEquals(mockWeather, updated.weather)
            assertEquals(mockWeather.lat to mockWeather.lon, updated.coordinates)
            assertEquals("", updated.searchQuery)
            assertEquals(emptyList(), updated.searchResults)

            coVerify { saveCoordinatesUseCase(mockWeather.lat, mockWeather.lon) }
            cancelAndIgnoreRemainingEvents()
        }
    }
}