package com.jae464.data.remote.model.response.recipe

import com.jae464.data.remote.model.response.user.UserInfoResponse
import kotlinx.serialization.Serializable

@Serializable
data class RecipeLikeResponse(
    val id: Long,
    val recipe: RecipePreviewResponse,
    val user: UserInfoResponse,
)

