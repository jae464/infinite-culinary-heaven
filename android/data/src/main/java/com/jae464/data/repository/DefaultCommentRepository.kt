package com.jae464.data.repository

import com.jae464.data.remote.api.CommentService
import com.jae464.data.remote.model.request.CommentCreateRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.domain.model.Comment
import com.jae464.domain.repository.CommentRepository
import javax.inject.Inject

class DefaultCommentRepository @Inject constructor(
    private val commentService: CommentService
) : CommentRepository {

    override suspend fun addComment(recipeId: Long, content: String): Result<Comment> {
        val response = commentService.createComment(CommentCreateRequest(recipeId, content))
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                Result.success(responseBody.toDomain())
            } else {
                Result.failure(Exception("Response body is null"))
            }
        } else {
            Result.failure(Exception("Failed to create comment"))
        }
    }

    override suspend fun getCommentsByRecipeId(recipeId: Long): Result<List<Comment>> {
        val response = commentService.getCommentsByRecipeId(recipeId)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                Result.success(responseBody.comments.map { it.toDomain() })
            } else {
                Result.failure(Exception("Response body is null"))
            }
        } else {
            Result.failure(Exception("Failed to fetch comments"))
        }

    }
}