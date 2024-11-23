package com.jae464.data.repository

import android.util.Log
import com.jae464.data.remote.api.RecipeService
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.repository.RecipeRepository
import javax.inject.Inject

class DefaultRecipeRepository @Inject constructor(
    private val recipeService: RecipeService
): RecipeRepository {

    override suspend fun getRecipePreviews(): Result<List<RecipePreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipePreviewsByContestId(contestId: Long): Result<List<RecipePreview>> {
        Log.d("DefaultRecipeRepository", "getRecipePreviewsByContestId")
        val response = recipeService.getRecipePreviews(contestId)
        Log.d("DefaultRecipeRepository", response.toString())
        return if (response.isSuccessful) {
            val recipeResponses = response.body()
            if (recipeResponses != null) {
                Result.success(recipeResponses.toDomain())
            } else {
                Result.failure(
                    Exception(
                        makeErrorResponse(
                        response.code(),
                        response.message(),
                        response.errorBody().toString())
                    )
                )
            }
        } else {
            Log.d("DefaultRecipeRepository", response.toString())
            Result.failure(Exception("network error"))
        }
    }

    override suspend fun getRecipeById(id: Long): Result<Recipe> {
        TODO("Not yet implemented")
    }
}