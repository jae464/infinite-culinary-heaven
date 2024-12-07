package com.jae464.data.remote.model.response

import com.jae464.domain.model.RecipePreview
import kotlinx.serialization.Serializable

@Serializable
data class RecipePreviewsResponse(
    val recipes: List<RecipePreviewResponse>
)

fun RecipePreviewsResponse.toDomain(): List<RecipePreview> {
    return recipes.map { it.toDomain() }
}
