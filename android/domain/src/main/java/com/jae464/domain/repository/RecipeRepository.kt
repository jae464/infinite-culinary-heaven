package com.jae464.domain.repository

import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview

interface RecipeRepository {
    suspend fun getRecipePreviews(): Result<List<RecipePreview>>
    suspend fun getRecipePreviewsByContestId(contestId: Long): Result<List<RecipePreview>>
    suspend fun getRecipeById(id: Long): Result<Recipe>

}