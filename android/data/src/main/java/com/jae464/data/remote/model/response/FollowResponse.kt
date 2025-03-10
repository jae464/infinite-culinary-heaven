package com.jae464.data.remote.model.response

import com.jae464.domain.model.Follow
import kotlinx.serialization.Serializable

@Serializable
data class FollowResponse(
    val userId: Long,
    val nickname: String
)

fun FollowResponse.toDomain(): Follow {
    return Follow(userId = userId, nickname = nickname)
}
