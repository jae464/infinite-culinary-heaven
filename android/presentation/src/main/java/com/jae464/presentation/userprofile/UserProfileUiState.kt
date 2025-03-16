package com.jae464.presentation.userprofile

import com.jae464.domain.model.RecipePreview
import com.jae464.domain.model.UserInfo

data class UserProfileUiState(
    val userInfo: UserInfo? = null,
    val isFollowing: Boolean = false,
    val isMe: Boolean = false,
    val recipePreviews: List<RecipePreview> = emptyList(),
    val isLoading: Boolean = false
)