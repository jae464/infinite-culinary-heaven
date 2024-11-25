package com.jae464.data.repository

import com.jae464.domain.model.UserInfo
import com.jae464.domain.repository.AuthRepository
import javax.inject.Inject

class FakeAuthRepository @Inject constructor() : AuthRepository {
    override suspend fun getUserInfo(): Result<UserInfo> {
        return Result.success(
            UserInfo(
                name = "이민재",
                profileImageUrl = "https://www.studiopeople.kr/common/img/default_profile.png"
            )
        )
    }
}