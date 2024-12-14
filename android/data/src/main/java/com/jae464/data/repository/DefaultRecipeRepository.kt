package com.jae464.data.repository

import android.util.Log
import androidx.core.net.toUri
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jae464.data.remote.api.RecipeService
import com.jae464.data.remote.api.SearchService
import com.jae464.data.remote.model.request.IngredientCreateRequest
import com.jae464.data.remote.model.request.RecipeCreateRequest
import com.jae464.data.remote.model.request.StepCreateRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.model.Step
import com.jae464.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class DefaultRecipeRepository @Inject constructor(
    private val recipeService: RecipeService,
    private val searchService: SearchService
) : RecipeRepository {

    override suspend fun getRecipePreviews(): Result<List<RecipePreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipePreviewsByContestId(page: Int, contestId: Long): Result<List<RecipePreview>> {
        return handleResponse {
            recipeService.getRecipePreviews(page = page, contestId = contestId)
        }.mapCatching { response ->
            response.toDomain()
        }
    }


    override suspend fun getRecipeById(id: Long): Result<Recipe> {
        return handleResponse {
            recipeService.getRecipeById(id)
        }.mapCatching { response ->
            response.toDomain()
        }
    }

    override suspend fun likeRecipe(recipeId: Long): Result<Unit> {
        return handleResponse {
            recipeService.likeRecipe(recipeId)
        }.mapCatching { response ->
            Result.success(Unit)
        }
    }

    override suspend fun unlikeRecipe(recipeId: Long): Result<Unit> {
        return handleResponse {
            recipeService.unlikeRecipe(recipeId)
        }.mapCatching { response ->
            Result.success(Unit)
        }
    }

    override suspend fun registerRecipe(
        images: List<File>,
        thumbnailImage: String?,
        title: String,
        description: String,
        ingredients: List<Ingredient>,
        steps: List<Step>,
        contestId: Long
    ): Result<Unit> {

        Log.d(
            "DefaultRecipeRepository",
            "images: $images thumbnailImage: $thumbnailImage title: $title description: $description ingredients: $ingredients steps: $steps"
        )

        val request = RecipeCreateRequest(
            thumbnailImage = thumbnailImage?.toUri()?.lastPathSegment ?: "",
            title = title,
            description = description,
            ingredients = ingredients.map {
                IngredientCreateRequest(
                    name = it.name,
                    quantity = it.quantity
                )
            },
            steps = steps.map {
                StepCreateRequest(
                    step = it.step,
                    description = it.description,
                    imageUrl = it.imageUrl?.toUri()?.lastPathSegment
                )
            },
            imageUrl = thumbnailImage?.toUri()?.lastPathSegment ?: "",
            contestId = contestId
        )

        val files = images.map {
            val fileBody = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images", it.name, fileBody)
        }

        val body = Json.encodeToString(RecipeCreateRequest.serializer(), request)
            .toRequestBody("application/json".toMediaType())

        return handleResponse {
            recipeService.postRecipe(images = files, body = body)
        }.mapCatching {
            Result.success(Unit)
        }
    }

    override suspend fun deleteRecipeById(recipeId: Long): Result<Unit> {
        return handleResponse {
            recipeService.deleteRecipeById(recipeId)
        }.mapCatching {
            Result.success(Unit)
        }
    }

    override suspend fun searchByKeyword(page: Int, keyword: String): Result<List<RecipePreview>> {
        return handleResponse {
            searchService.searchRecipes(page = page, keyword = keyword)
        }.mapCatching { response ->
            response.toDomain()
        }
    }
}