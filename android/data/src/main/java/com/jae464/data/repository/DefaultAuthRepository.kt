package com.jae464.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jae464.data.remote.api.AuthService
import com.jae464.data.remote.model.request.LoginRequest
import com.jae464.data.remote.model.request.RefreshTokenRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.TokenInfo
import com.jae464.domain.model.UserInfo
import com.jae464.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val authService: AuthService
) : AuthRepository {

    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    override suspend fun login(accessToken: String, oauth2Type: String): Result<TokenInfo> {

        val response = authService.login(LoginRequest(accessToken = accessToken, oauth2Type = oauth2Type))
        Log.d("DefaultAuthRepository", response.toString())

        return if (response.isSuccessful) {
            val loginResponse = response.body()
            if (loginResponse != null) {
                saveAccessToken(loginResponse.accessToken)
                saveRefreshToken(loginResponse.refreshToken)
                Result.success(loginResponse.toDomain())
            } else {
                Result.failure(
                    Exception(makeErrorResponse(
                        response.code(),
                        response.message(),
                        response.errorBody().toString()
                    ))
                )
            }
        } else {
            Result.failure(Exception("network error"))
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
        val response = authService.refreshToken(RefreshTokenRequest(refreshToken))

        return if (response.isSuccessful) {
            val loginResponse = response.body()
            if (loginResponse != null) {
                saveAccessToken(loginResponse.accessToken)
                saveRefreshToken(loginResponse.refreshToken)
                Result.success(loginResponse.toDomain())
            } else {
                Result.failure(
                    Exception(makeErrorResponse(
                        response.code(),
                        response.message(),
                        response.errorBody().toString()
                    ))
                )
            }
        } else {
            Result.failure(Exception("network error"))
        }
    }

}