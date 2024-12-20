package com.jae464.domain.repository

import com.jae464.domain.model.DeviceToken

interface DeviceTokenRepository {
    suspend fun updateDeviceToken(token: String): Result<DeviceToken>
}