package com.jae464.data.repository

import com.jae464.domain.model.UserInfo
import com.jae464.domain.repository.UserRepository
import javax.inject.Inject

class FakeUserRepository @Inject constructor() : UserRepository {
    override suspend fun getUserInfo(): UserInfo {
        return UserInfo(
            name = "이민재",
            profileImageUrl = "https://www.studiopeople.kr/common/img/default_profile.png"
        )
    }
}