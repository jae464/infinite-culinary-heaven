package com.jae464.data.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.jae464.data.di.SettingDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingLocalDataSource @Inject constructor(
    @SettingDataStore private val dataStore: DataStore<Preferences>
) {
    suspend fun setNotificationSetting(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY] = enabled
        }
    }

    fun getNotificationSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[NOTIFICATION_KEY] ?: false
        }
    }

    companion object {
        private val NOTIFICATION_KEY = booleanPreferencesKey("notification")
    }

}