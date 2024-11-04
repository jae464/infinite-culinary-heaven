package com.jae464.data.remote.model.response

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ContestsResponse(
    val contests: List<ContestResponse>
)

fun ContestsResponse.toDomain() = contests.map { it.toDomain() }

