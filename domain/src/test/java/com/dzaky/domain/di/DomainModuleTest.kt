@file:OptIn(KoinExperimentalAPI::class)

package com.dzaky.domain.di

import com.dzaky.core_ui.util.WeDispatchers
import com.dzaky.domain.repository.WeatherRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class DomainModuleTest : KoinTest {

    private val testModule = module {
        single<WeatherRepository> { mockk() }
        single {
            WeDispatchers(
                io = Dispatchers.Unconfined,
                main = Dispatchers.Unconfined
            )
        }
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
    fun `verify domain module configuration`() {
        domainModule.verify(extraTypes = listOf(
            WeatherRepository::class,
            WeDispatchers::class
        ))
    }
}