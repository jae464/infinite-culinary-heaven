package com.jae464.data.repository

import com.jae464.data.local.datasource.AuthLocalDataSource
import com.jae464.data.remote.api.AuthService
import com.jae464.data.remote.model.request.LoginRequest
import com.jae464.data.remote.model.request.RefreshTokenRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.domain.model.TokenInfo
import com.jae464.domain.repository.AuthRepository
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authService: AuthService,
) : AuthRepository {

    override suspend fun login(accessToken: String, oauth2Type: String): Result<TokenInfo> {
        return handleResponse {
            authService.login(LoginRequest(accessToken = accessToken, oauth2Type = oauth2Type))
        }.mapCatching {
            authLocalDataSource.saveAccessToken(it.accessToken)
            authLocalDataSource.saveRefreshToken(it.refreshToken)
            it.toDomain()
        }
    }

    override suspend fun logout() {
        authLocalDataSource.removeAccessToken()
        authLocalDataSource.removeRefreshToken()
    }

    override suspend fun saveAccessToken(accessToken: String) {
        authLocalDataSource.saveAccessToken(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        authLocalDataSource.saveRefreshToken(refreshToken)
    }

    override suspend fun getAccessToken(): String {
        return authLocalDataSource.getAccessToken()
    }

    override suspend fun getRefreshToken(): String {
        return authLocalDataSource.getRefreshToken()
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