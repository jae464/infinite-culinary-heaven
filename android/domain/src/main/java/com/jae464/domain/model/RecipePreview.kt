package com.jae464.domain.model

data class RecipePreview(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val description: String,
    val score: Float,
    val author: String,
)
