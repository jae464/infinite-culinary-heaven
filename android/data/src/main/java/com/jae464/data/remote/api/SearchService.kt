package com.jae464.data.remote.api

import com.jae464.data.remote.model.response.RecipePreviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/search/recipes")
    suspend fun searchRecipes(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("keyword") keyword: String
    ): Response<RecipePreviewsResponse>

}