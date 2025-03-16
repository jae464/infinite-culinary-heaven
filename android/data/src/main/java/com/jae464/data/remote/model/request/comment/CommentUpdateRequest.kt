package com.jae464.data.remote.model.request.comment

import kotlinx.serialization.Serializable

@Serializable
data class CommentUpdateRequest(
    val content: String
)
