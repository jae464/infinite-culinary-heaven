package com.jae464.domain.repository

import com.jae464.domain.model.Comment

interface CommentRepository {
    suspend fun addComment(recipeId: Long, content: String): Result<Comment>
    suspend fun getCommentsByRecipeId(recipeId: Long): Result<List<Comment>>
}