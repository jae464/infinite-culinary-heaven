package com.jae464.data.remote.model.response.bookmark

import com.jae464.data.remote.model.response.recipe.RecipePreviewResponse
import com.jae464.data.remote.model.response.recipe.toDomain
import com.jae464.domain.model.BookMark
import kotlinx.serialization.Serializable

@Serializable
data class BookMarkResponse(
    val id: Long,
    val recipe: RecipePreviewResponse,
    val userId: Long,
)

fun BookMarkResponse.toDomain(): BookMark {
    return BookMark(
        id = id,
        recipe = recipe.toDomain(),
        userId = userId
    )
}