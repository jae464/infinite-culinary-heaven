package com.jae464.data.remote.model.response.user

import com.jae464.domain.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val followerCount: Int?,
    val followingCount: Int?
)

fun UserInfoResponse.toDomain(): UserInfo {
    return UserInfo(
        id = id,
        name = nickname,
        profileImageUrl = profileImageUrl,
        followerCount = followerCount,
        followingCount = followingCount
    )
}
