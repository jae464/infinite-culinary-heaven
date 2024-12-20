package com.jae464.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jae464.data.remote.api.AuthService
import com.jae464.data.remote.model.request.LoginRequest
import com.jae464.data.remote.model.request.RefreshTokenRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.domain.model.TokenInfo
import com.jae464.domain.model.UserInfo
import com.jae464.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val authService: AuthService,
) : AuthRepository {

    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    override suspend fun login(accessToken: String, oauth2Type: String): Result<TokenInfo> {
        return handleResponse {
            authService.login(LoginRequest(accessToken = accessToken, oauth2Type = oauth2Type))
        }.mapCatching {
            saveAccessToken(it.accessToken)
            saveRefreshToken(it.refreshToken)
            it.toDomain()
        }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    override suspend fun getUserInfo(): Result<UserInfo> {
        // todo change
        return Result.success(
            UserInfo(
                id = 1,
                name = "이민재",
                profileImageUrl = "https://www.studiopeople.kr/common/img/default_profile.png"
            )
        )
    }

    override suspend fun getAccessToken(): String {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }.first() ?: ""
    }

    override suspend fun getRefreshToken(): String {
        return dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }.first() ?: ""
    }

    override suspend fun refreshToken(): Result<TokenInfo> {
        val refreshToken = getRefreshToken()
        return handleResponse {
            authService.refreshToken(RefreshTokenRequest(refreshToken))
        }.mapCatching {
            saveAccessToken(it.accessToken)
            saveRefreshToken(it.refreshToken)
            it.toDomain()
        }
    }

}