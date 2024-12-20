package com.jae464.data.repository

import com.jae464.data.remote.api.DeviceTokenService
import com.jae464.data.remote.model.request.DeviceTokenUpdateRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.domain.model.DeviceToken
import com.jae464.domain.repository.DeviceTokenRepository
import javax.inject.Inject

class DefaultDeviceTokenRepository @Inject constructor(
    private val deviceTokenService: DeviceTokenService
) : DeviceTokenRepository {

    override suspend fun updateDeviceToken(token: String): Result<DeviceToken> {
        return handleResponse {
            deviceTokenService.updateDeviceToken(DeviceTokenUpdateRequest(token))
        }.mapCatching {
            it.toDomain()
        }
    }

}