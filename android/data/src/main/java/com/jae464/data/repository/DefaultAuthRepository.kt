package com.jae464.data.repository

import com.jae464.domain.model.UserInfo
import com.jae464.domain.repository.AuthRepository

class DefaultAuthRepository : AuthRepository {
    override suspend fun getUserInfo(): Result<UserInfo> {
        TODO("Not yet implemented")
    }
}