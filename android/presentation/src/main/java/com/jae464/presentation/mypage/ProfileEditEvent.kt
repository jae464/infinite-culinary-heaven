package com.jae464.presentation.mypage

sealed interface ProfileEditEvent {
    data object UpdateProfileSuccess : ProfileEditEvent
}