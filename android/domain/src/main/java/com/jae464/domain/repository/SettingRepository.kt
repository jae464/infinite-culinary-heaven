package com.jae464.domain.repository

interface SettingRepository {
    suspend fun getNotificationSetting(): Boolean
}