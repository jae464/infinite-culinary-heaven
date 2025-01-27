package com.jae464.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getNotificationSetting(): Flow<Boolean>
    suspend fun setNotificationSetting(enabled: Boolean)
}