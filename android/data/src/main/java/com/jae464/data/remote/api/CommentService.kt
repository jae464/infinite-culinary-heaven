package com.jae464.data.remote.api

import com.jae464.data.remote.model.request.comment.CommentCreateRequest
import com.jae464.data.remote.model.request.comment.CommentUpdateRequest
import com.jae464.data.remote.model.response.comment.CommentResponse
import com.jae464.data.remote.model.response.comment.CommentsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentService {

    @POST("/comments")
    suspend fun createComment(
        @Body request: CommentCreateRequest,
    ): Response<CommentResponse>

    @GET("/comments")
    suspend fun getCommentsByRecipeId(
        @Query("recipeId") recipeId: Long,
    ): Response<CommentsResponse>

    @PATCH("/comments/{commentId}")
    suspend fun updateComment(
        @Path("commentId") commentId: Long,
        @Body request: CommentUpdateRequest,
    ): Response<CommentResponse>

    @DELETE("/comments/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: Long,
    ): Response<Unit>

}