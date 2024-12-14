package com.jae464.data.repository

import com.jae464.data.remote.api.CommentService
import com.jae464.data.remote.model.request.CommentCreateRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.domain.model.Comment
import com.jae464.domain.repository.CommentRepository
import javax.inject.Inject

class DefaultCommentRepository @Inject constructor(
    private val commentService: CommentService
) : CommentRepository {

    override suspend fun addComment(recipeId: Long, content: String): Result<Comment> {
        return handleResponse {
            commentService.createComment(CommentCreateRequest(recipeId, content))
        }.mapCatching {
            it.toDomain()
        }
    }

    override suspend fun getCommentsByRecipeId(recipeId: Long): Result<List<Comment>> {
        return handleResponse {
            commentService.getCommentsByRecipeId(recipeId)
        }.mapCatching { response ->
            response.comments.map { it.toDomain() }
        }
    }
}