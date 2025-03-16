package com.jae464.data.remote.model.request.comment

import kotlinx.serialization.Serializable

@Serializable
data class CommentCreateRequest(
    val recipeId: Long,
    val content: String
)
