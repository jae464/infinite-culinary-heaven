package com.jae464.data.remote.model.response

import com.jae464.domain.model.TokenInfo
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)

fun LoginResponse.toDomain(): TokenInfo {
    return TokenInfo(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}
