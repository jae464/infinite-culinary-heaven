package com.jae464.domain.repository

import com.jae464.domain.model.TokenInfo
import com.jae464.domain.model.UserInfo

interface AuthRepository {

    suspend fun login(accessToken: String, oauth2Type: String): Result<TokenInfo>
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun getUserInfo(): Result<UserInfo>
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun refreshToken(): Result<TokenInfo>

}