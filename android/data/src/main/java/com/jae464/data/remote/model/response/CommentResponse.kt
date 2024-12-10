package com.jae464.data.remote.model.response

import com.jae464.domain.model.Comment
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CommentResponse(
    val id: Long,
    val content: String,
    val createdAt: String,
    val userInfo: UserInfoResponse
)

fun CommentResponse.toDomain() = Comment(
    id = id,
    content = content,
    createdAt = LocalDateTime.parse(createdAt),
    userInfo = userInfo.toDomain()
)