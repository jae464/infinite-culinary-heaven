package com.jae464.presentation.userprofile

import com.jae464.domain.model.UserInfo

data class UserProfileUiState(
    val userInfo: UserInfo? = null,
    val isFollowing: Boolean = false,
)