package com.dzaky.core_ui.preference

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.dzaky.core_ui.util.orEmpty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "weather_preferences")

class PreferencesDataStoreImpl(context: Context) : PreferencesStorage {
    private val dataStore = context.dataStore
    private val latitudeKey = doublePreferencesKey("latitude")
    private val longitudeKey = doublePreferencesKey("longitude")

    override suspend fun saveCoordinates(lat: Double, lon: Double) {
        dataStore.edit { preferences ->
            preferences[latitudeKey] = lat
            preferences[longitudeKey] = lon
        }
    }

    override fun getCoordinates(): Flow<Pair<Double, Double>> {
        return dataStore.data.map { preferences ->
            Pair(preferences[latitudeKey].orEmpty(), preferences[longitudeKey].orEmpty())
        }
    }
}