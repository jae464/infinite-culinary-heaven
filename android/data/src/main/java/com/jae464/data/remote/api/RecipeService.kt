package com.jae464.data.remote.api

import com.jae464.data.remote.model.response.RecipePreviewResponse
import com.jae464.data.remote.model.response.RecipePreviewsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("/recipes")
    suspend fun getRecipePreviews(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20, // todo page 로직 구현후 추후 수정
        @Query("contestId") contestId: Long
    ): Response<RecipePreviewsResponse>

    @GET("/recipes/{recipeId}")
    suspend fun getRecipeById(@Path("recipeId") recipeId: Long): Response<RecipePreviewResponse>

    @Multipart
    @POST("/recipes")
    suspend fun postRecipe(
        @Part images: List<MultipartBody.Part>,
        @Part("request") body: RequestBody
    ): Response<Unit>

    @DELETE("/recipes/{recipeId}")
    suspend fun deleteRecipeById(@Path("recipeId") recipeId: Long): Response<Unit>
}