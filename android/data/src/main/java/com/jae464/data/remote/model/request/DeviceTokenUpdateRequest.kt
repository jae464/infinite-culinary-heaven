package com.jae464.data.remote.model.request

import com.jae464.domain.model.DeviceToken
import kotlinx.serialization.Serializable

@Serializable
data class DeviceTokenUpdateRequest(
    val token: String
)

fun DeviceTokenUpdateRequest.toDomain() = DeviceToken(
    token = token
)
