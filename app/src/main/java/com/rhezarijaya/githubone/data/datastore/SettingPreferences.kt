package com.rhezarijaya.githubone.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.rhezarijaya.githubone.data.datastore.SettingPreferences.Companion.PREFERENCE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val darkModeKey = booleanPreferencesKey("dark_mode")

    fun getDarkModeEnabled(): Flow<Boolean> {
        return dataStore.data.map {
            it[darkModeKey] ?: false
        }
    }

    suspend fun setDarkModeEnabled(isDarkModeActive: Boolean) {
        dataStore.edit {
            it[darkModeKey] = isDarkModeActive
        }
    }

    companion object {
        const val PREFERENCE_NAME = "githubone_setting"

        @Volatile
        private var instance: SettingPreferences? = null

        @JvmStatic
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            if (instance == null) {
                synchronized(this) {
                    instance = SettingPreferences(dataStore)
                }
            }

            return instance!!
        }
    }
}