package com.dzaky.data.network

import com.dzaky.core_ui.model.WeResult
import com.dzaky.core_ui.util.orEmpty
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class KtorRemoteWeatherDataSourceTest {
    private lateinit var mockEngine: MockEngine
    private lateinit var httpClient: HttpClient
    private lateinit var dataSource: RemoteWeatherDataSource

    @Before
    fun setup() {
        mockEngine = MockEngine { request ->
            when {
                request.url.encodedPath.contains("/search.json") -> {
                    respond(
                        content = """[{"id":1,"name":"London","lat":51.5074,"lon":-0.1278}]""",
                        headers = headersOf(
                            HttpHeaders.ContentType,
                            ContentType.Application.Json.toString()
                        )
                    )
                }

                request.url.encodedPath.contains("/current.json") -> {
                    respond(
                        content = """{"location":{"name":"London","lat":51.5074,"lon":-0.1278},"current":{"temp_c":20.0,"condition":{"icon":"sunny.png"},"humidity":70,"feelslike_c":21.0,"uv":4.0}}""",
                        headers = headersOf(
                            HttpHeaders.ContentType,
                            ContentType.Application.Json.toString()
                        )
                    )
                }

                else -> error("Unhandled ${request.url.encodedPath}")
            }
        }

        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }

        dataSource = KtorRemoteWeatherDataSource(httpClient)
    }

    @Test
    fun `searchLocation when successful should return weather list`() = runTest {
        // When
        val result = dataSource.searchLocation("London")

        // Then
        assert(result is WeResult.Success)
        val weather = (result as WeResult.Success).data.first()
        assertEquals("London", weather.name)
        assertEquals(51.5074, weather.lat.orEmpty(), 0.0001)
        assertEquals(-0.1278, weather.lon.orEmpty(), 0.0001)
    }

    @Test
    fun `getCurrentWeather when successful should return weather`() = runTest {
        // When
        val result = dataSource.getCurrentWeather(51.5074, -0.1278)

        // Then
        assert(result is WeResult.Success)
        val weather = (result as WeResult.Success).data
        assertEquals("London", weather.location?.name.orEmpty())
        assertEquals(20.0, weather.current?.tempInCelsius.orEmpty(), 0.1)
        assertEquals(70, weather.current?.humidity.orEmpty())
        assertEquals(21.0, weather.current?.feelsLikeInCelsius.orEmpty(), 0.1)
        assertEquals(4.0, weather.current?.uv.orEmpty(), 0.1)
    }
}