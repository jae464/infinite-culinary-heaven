package com.jae464.data.remote.model.response.contest

import kotlinx.serialization.Serializable

@Serializable
data class ContestsResponse(
    val contests: List<ContestResponse>
)

fun ContestsResponse.toDomain() = contests.map { it.toDomain() }

