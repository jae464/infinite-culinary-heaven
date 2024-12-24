package com.jae464.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RecipeUpdateRequest(
    val title: String,
    val thumbnailImage: String,
    val description: String,
    val ingredients: List<IngredientUpdateRequest>,
    val steps: List<StepUpdateRequest>,
)

@Serializable
data class IngredientUpdateRequest(
    val name: String,
    val quantity: String,
)

@Serializable
data class StepUpdateRequest(
    val step: Int,
    val description: String,
    val imageName: String?,
    val imageUrl: String?
)