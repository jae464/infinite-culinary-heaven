package com.jae464.domain.model

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val userInfo: UserInfo
)
