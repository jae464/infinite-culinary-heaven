package com.jae464.presentation.mypage.editprofile

sealed interface ProfileEditEvent {
    data object UpdateProfileSuccess : ProfileEditEvent
    data object EmptyNickname : ProfileEditEvent
    data object TooLongNickname : ProfileEditEvent
}