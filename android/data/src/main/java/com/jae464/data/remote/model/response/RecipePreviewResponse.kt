package com.jae464.data.remote.model.response

import com.jae464.data.util.adjustLocalhostUrl
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.model.Step
import kotlinx.serialization.Serializable

@Serializable
data class RecipePreviewResponse(
    val id: Long,
    val title: String,
    val description: String,
    val thumbnailImageUrl: String,
    val writerInfoResponse: WriterInfoResponse,
//    val steps: List<StepResponse>,
//    val ingredients: List<IngredientResponse>,
    val contest: ContestResponse,
    val bookMarkCounts: Int,
    val likeCounts: Int,
//    val isOwner: Boolean?
)

fun RecipePreviewResponse.toDomain() = RecipePreview(
    id = id,
    title = title,
    imageUrl = adjustLocalhostUrl(thumbnailImageUrl),
    description = description,
    score = 5f,
    bookMarkCounts = bookMarkCounts,
    likeCounts = likeCounts,
    author = writerInfoResponse.nickname,
)


