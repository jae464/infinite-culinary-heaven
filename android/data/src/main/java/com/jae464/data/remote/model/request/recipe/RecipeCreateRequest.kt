package com.jae464.data.remote.model.request.recipe

import kotlinx.serialization.Serializable

@Serializable
data class RecipeCreateRequest(
    val title: String,
    val thumbnailImage: String,
    val description: String,
    val ingredients: List<IngredientCreateRequest>,
    val steps: List<StepCreateRequest>,
    val contestId: Long
)

@Serializable
data class IngredientCreateRequest(
    val name: String,
    val quantity: String,
)

@Serializable
data class StepCreateRequest(
    val step: Int,
    val description: String,
    val imageName: String?,
)