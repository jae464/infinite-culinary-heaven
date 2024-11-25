package com.jae464.domain.repository

import com.jae464.domain.model.UserInfo

interface AuthRepository {
    suspend fun getUserInfo(): Result<UserInfo>
}