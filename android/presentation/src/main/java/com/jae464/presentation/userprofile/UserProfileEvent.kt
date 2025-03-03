package com.jae464.presentation.userprofile

sealed interface UserProfileEvent {
    data object FetchUserInfoFailed : UserProfileEvent
}