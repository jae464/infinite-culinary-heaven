package com.jae464.data.remote.model.response.recipe

import com.jae464.data.remote.model.response.contest.ContestResponse
import com.jae464.data.util.adjustLocalhostUrl
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.Step
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    val id: Long,
    val title: String,
    val description: String,
    val thumbnailImageUrl: String,
    val writerInfoResponse: WriterInfoResponse,
    val steps: List<StepResponse>,
    val ingredients: List<IngredientResponse>,
    val contest: ContestResponse,
    val bookMarkCounts: Int,
    val likeCounts: Int,
    val isBookMarked: Boolean,
    val isLiked: Boolean,
    val isOwner: Boolean?
)

@Serializable
data class StepResponse(
    val id: Long,
    val step: Int,
    val description: String,
    val imageUrl: String?
)

@Serializable
data class IngredientResponse(
    val id: Long,
    val name: String,
    val quantity: String
)

fun RecipeResponse.toDomain() = Recipe(
    id = id,
    title = title,
    imageUrl = adjustLocalhostUrl(thumbnailImageUrl),
    description = description,
    score = 5f,
    writeInfo = writerInfoResponse.toDomain(),
    ingredients = ingredients.map { it.toDomain() },
    steps = steps.map { it.toDomain() },
    bookMarkCounts = bookMarkCounts,
    likeCounts = likeCounts,
    isLiked = isLiked,
    isBookMarked = isBookMarked,
    isOwner = isOwner ?: false
)

fun IngredientResponse.toDomain() = Ingredient(
    name = name,
    quantity = quantity
)

fun StepResponse.toDomain() = Step(
    step = step,
    description = description,
    imageUrl = imageUrl
)
