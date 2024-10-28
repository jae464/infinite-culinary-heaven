package com.jae464.domain.repository

import com.jae464.domain.model.UserInfo

interface UserRepository {
    suspend fun getUserInfo(): UserInfo
}