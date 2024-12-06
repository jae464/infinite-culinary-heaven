package com.jae464.data.repository

import android.util.Log
import androidx.core.net.toUri
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
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
    private val recipeService: RecipeService
) : RecipeRepository {

    override suspend fun getRecipePreviews(): Result<List<RecipePreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipePreviewsByContestId(page: Int, contestId: Long): Result<List<RecipePreview>> {
        Log.d("DefaultRecipeRepository", "getRecipePreviewsByContestId")
        val response = recipeService.getRecipePreviews(page = page, contestId = contestId)
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

    override fun getPagedRecipePreviewsByContestId(contestId: Long): PagingSource<Int, RecipePreview> {
        return object : PagingSource<Int, RecipePreview>() {
            override fun getRefreshKey(state: PagingState<Int, RecipePreview>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipePreview> {
                return try {

                    val page = params.key ?: 0
                    Log.d("DefaultRecipeRepository", "page: $page")

                    val response = recipeService.getRecipePreviews(
                        page = page,
                        size = params.loadSize,
                        contestId = contestId
                    )

                    if (response.isSuccessful) {
                        val recipePreviews = response.body()?.toDomain() ?: emptyList()
                        LoadResult.Page(
                            data = recipePreviews,
                            prevKey = if (page == 0) null else page - 1,
                            nextKey = if (recipePreviews.isEmpty()) null else page + 1
                        )
                    } else {
                        LoadResult.Error(Exception("Network Error: ${response.code()} ${response.message()}"))
                    }
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

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