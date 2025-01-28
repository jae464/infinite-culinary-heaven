package com.jae464.presentation.mypage.setting

sealed interface SettingEvent {
    data object LogoutSuccess : SettingEvent
}