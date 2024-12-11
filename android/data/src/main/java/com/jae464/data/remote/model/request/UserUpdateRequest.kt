package com.jae464.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    val userNickname: String
)
