package com.jae464.data.repository

import android.util.Log
import androidx.core.net.toUri
import com.jae464.data.remote.api.RecipeService
import com.jae464.data.remote.model.request.IngredientCreateRequest
import com.jae464.data.remote.model.request.RecipeCreateRequest
import com.jae464.data.remote.model.request.StepCreateRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.remote.model.response.toRecipeDomain
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.model.Step
import com.jae464.domain.repository.RecipeRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class DefaultRecipeRepository @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository {

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
                            response.errorBody().toString()
                        )
                    )
                )
            }
        } else {
            Log.d("DefaultRecipeRepository", response.toString())
            Result.failure(Exception("network error"))
        }
    }

    override suspend fun getRecipeById(id: Long): Result<Recipe> {
        val response = recipeService.getRecipeById(id)

        return if (response.isSuccessful) {
            val recipeResponse = response.body()
            if (recipeResponse != null) {
                Result.success(recipeResponse.toRecipeDomain())
            } else {
                Result.failure(
                    Exception(
                        makeErrorResponse(
                            response.code(),
                            response.message(),
                            response.errorBody().toString()
                        )
                    )
                )
            }
        } else {
            Result.failure(Exception("network error"))
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

        val response = recipeService.postRecipe(images = files, body = body)

        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception(makeErrorResponse(response.code(), response.message(), response.errorBody().toString())))
        }
    }

    override suspend fun deleteRecipeById(recipeId: Long): Result<Unit> {
        val response = recipeService.deleteRecipeById(recipeId)
        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception(makeErrorResponse(response.code(), response.message(), response.errorBody().toString())))
        }
    }
}