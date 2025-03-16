package com.jae464.data.remote.model.response.user

import kotlinx.serialization.Serializable

@Serializable
data class FollowsResponse(
    val follows: List<FollowResponse>
)
