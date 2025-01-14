package com.jae464.presentation.mypage

sealed interface MyPageEvent {
    data object LogoutFinished : MyPageEvent
}