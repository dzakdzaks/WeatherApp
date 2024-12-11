@file:OptIn(KoinExperimentalAPI::class)

package com.dzaky.data.di

import com.dzaky.core_ui.preference.PreferencesStorage
import com.dzaky.data.network.KtorRemoteWeatherDataSource
import com.dzaky.data.network.RemoteWeatherDataSource
import com.dzaky.data.repository.WeatherRepositoryImpl
import com.dzaky.domain.repository.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class DataModuleTest : KoinTest {

    private val testModule = module {
        // Mock HttpClient with MockEngine
        single {
            HttpClient(MockEngine) {
                engine {
                    addHandler {
                        respond(
                            content = "{}",
                            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        )
                    }
                }
            }
        }

        // Mock PreferencesStorage
        single<PreferencesStorage> { mockk() }
    }

    @Before
    fun setUp() {
        startKoin {
            modules(testModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `verify data module configuration`() {
        dataModule.verify(
            extraTypes = listOf(
                HttpClient::class,
                PreferencesStorage::class
            )
        )
    }
}