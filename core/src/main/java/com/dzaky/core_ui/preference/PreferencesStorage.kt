package com.dzaky.core_ui.preference

import kotlinx.coroutines.flow.Flow

interface PreferencesStorage {
    suspend fun saveCoordinates(lat: Double, lon: Double)
    fun getCoordinates(): Flow<Pair<Double, Double>>
}