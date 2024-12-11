package com.jae464.domain.repository

import com.jae464.domain.model.UserInfo
import java.io.File

interface UserRepository {
    suspend fun getMyInfo(): Result<UserInfo>
    suspend fun updateProfile(nickname: String, image: File?): Result<UserInfo>
}