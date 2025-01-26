package com.jae464.data.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jae464.data.di.AuthDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalDataSource @Inject constructor(
    @AuthDataStore private val dataStore: DataStore<Preferences>
) {
    suspend fun saveAccessToken(
        accessToken: String
    ) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
    }
    suspend fun saveRefreshToken(
        refreshToken: String
    ) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun getAccessToken(): String {
        return dataStore.data.first()[ACCESS_TOKEN_KEY] ?: ""
    }

    suspend fun getRefreshToken(): String {
        return dataStore.data.first()[REFRESH_TOKEN_KEY] ?: ""
    }

    suspend fun removeAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
        }
    }
    suspend fun removeRefreshToken() {
        dataStore.edit { preferences ->
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }
}