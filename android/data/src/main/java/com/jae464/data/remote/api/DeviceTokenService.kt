package com.jae464.data.remote.api

import com.jae464.data.remote.model.request.DeviceTokenUpdateRequest
import com.jae464.data.remote.model.response.DeviceTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

interface DeviceTokenService {

    @PATCH("/device-token")
    suspend fun updateDeviceToken(
        @Body request: DeviceTokenUpdateRequest
    ): Response<DeviceTokenResponse>

}