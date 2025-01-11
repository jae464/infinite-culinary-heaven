package com.jae464.data.repository

import android.util.Log
import androidx.core.net.toUri
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jae464.data.remote.api.RecipeService
import com.jae464.data.remote.api.SearchService
import com.jae464.data.remote.model.request.IngredientCreateRequest
import com.jae464.data.remote.model.request.IngredientUpdateRequest
import com.jae464.data.remote.model.request.RecipeCreateRequest
import com.jae464.data.remote.model.request.RecipeUpdateRequest
import com.jae464.data.remote.model.request.StepCreateRequest
import com.jae464.data.remote.model.request.StepUpdateRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.model.Step
import com.jae464.domain.model.StepCreate
import com.jae464.domain.model.StepUpdate
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

    override suspend fun getMyRecipePreviews(page: Int): Result<List<RecipePreview>> {
        return handleResponse {
            recipeService.getMyRecipePreviews(page = page)
        }.mapCatching { response ->
            response.toDomain()
        }
    }

    override suspend fun getMyLikeRecipePreviews(page: Int): Result<List<RecipePreview>> {
        return handleResponse {
            recipeService.getMyLikesRecipePreviews(page = page)
        }.mapCatching { response ->
            response.recipeLikes.map { it.recipe.toDomain() }
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
        thumbnailImageName: String,
        title: String,
        description: String,
        ingredients: List<Ingredient>,
        steps: List<StepCreate>,
        contestId: Long
    ): Result<Unit> {

        Log.d(
            "DefaultRecipeRepository",
            "images: $images thumbnailImage: $thumbnailImageName title: $title description: $description ingredients: $ingredients steps: $steps"
        )

        Log.d("DefaultRecipeRepository", "url.toUri().lastPathSegment : ${thumbnailImageName?.toUri()?.lastPathSegment}")
        Log.d("DefaultRecipeRepository", "url : ${thumbnailImageName}")

        val request = RecipeCreateRequest(
            thumbnailImage = thumbnailImageName,
            title = title,
            description = description,
            ingredients = ingredients.map {
                IngredientCreateRequest(
                    name = it.name,
                    quantity = it.quantity
                )
            },
            steps = steps.map {
                Log.d("DefaultRecipeRepository", "url.toUri().lastPathSegment : ${it.imageName?.toUri()?.lastPathSegment}")
                Log.d("DefaultRecipeRepository", "url : ${it.imageName}")
                StepCreateRequest(
                    step = it.step,
                    description = it.description,
                    imageName = it.imageName
                )
            },
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

    override suspend fun updateRecipe(
        recipeId: Long,
        images: List<File>,
        thumbnailImage: String,
        title: String,
        description: String,
        ingredients: List<Ingredient>,
        steps: List<StepUpdate>
    ): Result<Unit> {
        val request = RecipeUpdateRequest(
            thumbnailImage = thumbnailImage,
            title = title,
            description = description,
            ingredients = ingredients.map {
                IngredientUpdateRequest(
                    name = it.name,
                    quantity = it.quantity
                )
            },
            steps = steps.map {
                StepUpdateRequest(
                    step = it.step,
                    description = it.description,
                    imageName = it.imageName,
                    imageUrl = it.imageUrl
                )
            },
        )

        val files = images.map {
            val fileBody = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images", it.name, fileBody)
        }

        val body = Json.encodeToString(RecipeUpdateRequest.serializer(), request)
            .toRequestBody("application/json".toMediaType())

        return handleResponse {
            recipeService.putRecipe(recipeId = recipeId, images = files, body = body)
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