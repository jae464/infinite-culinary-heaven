package com.jae464.presentation.mypage.editprofile

sealed interface ProfileEditIntent {
    data class UpdateNickname(val nickname: String) : ProfileEditIntent
    data class UpdateProfileImage(val profileImageUrl: String) : ProfileEditIntent
    data object UpdateProfile : ProfileEditIntent
}