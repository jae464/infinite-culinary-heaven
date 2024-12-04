package com.jae464.data.remote.model.response

import com.jae464.domain.model.BookMark
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class BookMarkResponse(
    val id: Long,
    val recipe: RecipePreviewResponse,
    val userId: Long,
)

fun BookMarkResponse.toDomain(): BookMark {
    return BookMark(
        id = id,
        recipe = recipe.toRecipePreviewDomain(),
        userId = userId
    )
}