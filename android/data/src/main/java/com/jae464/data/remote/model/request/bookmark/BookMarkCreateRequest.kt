package com.jae464.data.remote.model.request.bookmark

import kotlinx.serialization.Serializable

@Serializable
data class BookMarkCreateRequest(
    val recipeId: Long
)
