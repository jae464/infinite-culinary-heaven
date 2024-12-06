package com.jae464.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.model.Step
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RecipeRepository {
    suspend fun getRecipePreviews(): Result<List<RecipePreview>>
    suspend fun getRecipePreviewsByContestId(page: Int, contestId: Long): Result<List<RecipePreview>>
    fun getPagedRecipePreviewsByContestId(contestId: Long): PagingSource<Int, RecipePreview>
    suspend fun getRecipeById(id: Long): Result<Recipe>
    suspend fun registerRecipe(
        images: List<File>,
        thumbnailImage: String?,
        title: String,
        description: String,
        ingredients: List<Ingredient>,
        steps: List<Step>,
        contestId: Long
    ): Result<Unit>
    suspend fun deleteRecipeById(
        recipeId: Long
    ): Result<Unit>
}