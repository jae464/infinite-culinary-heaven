package com.jae464.data.remote.model.response

import com.jae464.domain.model.DeviceToken
import kotlinx.serialization.Serializable

@Serializable
data class DeviceTokenResponse(
    val token: String
)

fun DeviceTokenResponse.toDomain(): DeviceToken {
    return DeviceToken(token = token)
}