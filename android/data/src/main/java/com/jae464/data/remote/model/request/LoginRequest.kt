package com.jae464.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val accessToken: String,
    val oauth2Type: String
)
