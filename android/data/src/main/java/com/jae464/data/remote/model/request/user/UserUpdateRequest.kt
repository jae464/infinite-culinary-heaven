package com.jae464.data.remote.model.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    val userNickname: String
)
