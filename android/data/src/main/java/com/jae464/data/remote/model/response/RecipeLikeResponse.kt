package com.jae464.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RecipeLikeResponse(
    val id: Long,
    val recipe: RecipePreviewResponse,
    val user: UserInfoResponse,
)

