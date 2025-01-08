package com.jae464.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.model.Step
import com.jae464.domain.model.StepCreate
import com.jae464.domain.model.StepUpdate
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RecipeRepository {
    suspend fun getRecipePreviews(): Result<List<RecipePreview>>
    suspend fun getRecipePreviewsByContestId(page: Int, contestId: Long): Result<List<RecipePreview>>
    suspend fun getMyRecipePreviews(page: Int): Result<List<RecipePreview>>
    suspend fun getRecipeById(id: Long): Result<Recipe>
    suspend fun likeRecipe(recipeId: Long): Result<Unit>
    suspend fun unlikeRecipe(recipeId: Long): Result<Unit>
    suspend fun registerRecipe(
        images: List<File>,
        thumbnailImageName: String,
        title: String,
        description: String,
        ingredients: List<Ingredient>,
        steps: List<StepCreate>,
        contestId: Long
    ): Result<Unit>
    suspend fun updateRecipe(
        recipeId: Long,
        images: List<File>,
        thumbnailImage: String,
        title: String,
        description: String,
        ingredients: List<Ingredient>,
        steps: List<StepUpdate>,
    ): Result<Unit>
    suspend fun deleteRecipeById(
        recipeId: Long
    ): Result<Unit>
    suspend fun searchByKeyword(page: Int, keyword: String): Result<List<RecipePreview>>
}