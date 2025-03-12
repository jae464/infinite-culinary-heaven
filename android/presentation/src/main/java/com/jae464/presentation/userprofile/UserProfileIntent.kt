package com.jae464.presentation.userprofile

sealed interface UserProfileIntent {
    data object FollowUser : UserProfileIntent
    data object UnfollowUser : UserProfileIntent
}