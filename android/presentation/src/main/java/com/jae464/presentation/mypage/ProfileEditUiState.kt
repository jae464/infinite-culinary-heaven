package com.jae464.presentation.mypage

data class ProfileEditUiState(
    val nickname: String = "",
    val profileImageUrl: String? = null,
    val isNicknameChanged: Boolean = false,
    val isProfileImageChanged: Boolean = false
)
