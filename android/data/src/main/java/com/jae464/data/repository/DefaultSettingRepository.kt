package com.jae464.data.repository

import com.jae464.data.local.datasource.SettingLocalDataSource
import com.jae464.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultSettingRepository @Inject constructor(
    private val settingLocalDataSource: SettingLocalDataSource
): SettingRepository {

    override fun getNotificationSetting(): Flow<Boolean> {
        return settingLocalDataSource.getNotificationSetting()
    }

    override suspend fun setNotificationSetting(enabled: Boolean) {
        settingLocalDataSource.setNotificationSetting(enabled)
    }

}