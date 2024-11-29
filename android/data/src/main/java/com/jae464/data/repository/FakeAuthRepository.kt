package com.jae464.data.repository

import com.jae464.domain.model.TokenInfo
import com.jae464.domain.model.UserInfo
import com.jae464.domain.repository.AuthRepository
import javax.inject.Inject

class FakeAuthRepository @Inject constructor() : AuthRepository {
    override suspend fun login(accessToken: String, oauth2Type: String): Result<TokenInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAccessToken(accessToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserInfo(): Result<UserInfo> {
        return Result.success(
            UserInfo(
                name = "이민재",
                profileImageUrl = "https://www.studiopeople.kr/common/img/default_profile.png"
            )
        )
    }

    override suspend fun getAccessToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun getRefreshToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun refreshToken(): Result<TokenInfo> {
        TODO("Not yet implemented")
    }
}