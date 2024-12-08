package com.jae464.domain.model

import java.time.LocalDateTime

data class Contest(
    val id: Long,
    val title: String,
    val ingredient: String,
    val imageUrl: String,
    val description: String,
    val startDate: LocalDateTime,
)
