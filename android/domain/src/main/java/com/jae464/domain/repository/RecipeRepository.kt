package com.jae464.domain.repository

import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.model.Step
import java.io.File

interface RecipeRepository {
    suspend fun getRecipePreviews(): Result<List<RecipePreview>>
    suspend fun getRecipePreviewsByContestId(contestId: Long): Result<List<RecipePreview>>
    suspend fun getRecipeById(id: Long): Result<Recipe>
    suspend fun registerRecipe(
        images: List<File>,
        thumbnailImage: String?,
        title: String,
        description: String,
        ingredients: List<Ingredient>,
        steps: List<Step>
    ): Result<Unit>
}