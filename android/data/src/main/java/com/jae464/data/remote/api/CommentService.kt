package com.jae464.data.remote.api

import com.jae464.data.remote.model.request.CommentCreateRequest
import com.jae464.data.remote.model.response.CommentResponse
import com.jae464.data.remote.model.response.CommentsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

}