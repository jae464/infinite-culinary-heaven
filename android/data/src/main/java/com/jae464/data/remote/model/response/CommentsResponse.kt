package com.jae464.data.remote.model.response

import com.jae464.domain.model.Comment
import kotlinx.serialization.Serializable

@Serializable
data class CommentsResponse(
    val comments: List<CommentResponse>
)

fun CommentsResponse.toDomain(): List<Comment> {
    return comments.map { it.toDomain() }
}
