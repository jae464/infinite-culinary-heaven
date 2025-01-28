package com.jae464.presentation.mypage.setting

sealed interface SettingIntent {
    data class SetNotificationEnabled(val enabled: Boolean) : SettingIntent
    data object LogoutButtonClicked : SettingIntent

}