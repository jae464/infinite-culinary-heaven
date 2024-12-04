package com.jae464.data.remote.model.response

import com.jae464.domain.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class WriterInfoResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
)

fun WriterInfoResponse.toDomain(): UserInfo {
    return UserInfo(
        id = id,
        name = nickname,
        profileImageUrl = profileImageUrl
    )
}
