package com.jae464.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RecipeLikesResponse(
    val recipeLikes: List<RecipeLikeResponse>
)
