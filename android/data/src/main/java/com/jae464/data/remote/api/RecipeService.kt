package com.jae464.data.remote.api

import com.jae464.data.remote.model.response.RecipePreviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {
    @GET("/recipes")
    suspend fun getRecipePreviews(@Query("contestId") contestId: Long): Response<RecipePreviewsResponse>
}