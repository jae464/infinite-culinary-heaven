package com.jae464.data.remote.model.response

import com.jae464.data.util.adjustLocalhostUrl
import com.jae464.domain.model.RecipePreview
import kotlinx.serialization.Serializable

@Serializable
data class RecipePreviewResponse(
    val id: Long,
    val title: String,
    val description: String,
    val thumbnailImage: String,
    val steps: List<StepResponse>,
    val ingredients: List<IngredientResponse>,
    val contest: ContestResponse
)

fun RecipePreviewResponse.toDomain() = RecipePreview(
    id = id,
    title = title,
    imageUrl = adjustLocalhostUrl(thumbnailImage),
    description = description,
    score = 5f,
    author = "author",
)

@Serializable
data class StepResponse(
    val id: Long,
    val description: String,
    val imageUrl: String
)

@Serializable
data class IngredientResponse(
    val id: Long,
    val name: String,
    val quantity: String
)
