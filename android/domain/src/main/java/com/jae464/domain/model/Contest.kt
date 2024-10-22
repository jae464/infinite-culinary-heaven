package com.jae464.domain.model

import java.time.LocalDate

data class Contest(
    val id: Long,
    val ingredient: String,
    val imageUrl: String,
    val description: String,
    val startDate: LocalDate,
)
