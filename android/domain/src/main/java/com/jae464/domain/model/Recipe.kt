package com.jae464.domain.model

data class Recipe(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val description: String,
    val score: Float,
    val author: String,
    val ingredients: List<Ingredient>,
    val steps: List<Step>,
    val bookMarkCounts: Int,
    val likeCounts: Int,
    val isBookMarked: Boolean,
    val isLiked: Boolean,
    val isOwner: Boolean
)

data class Ingredient(
    val name: String,
    val quantity: String
)

data class Step(
    val step: Int,
    val description: String,
    val imageUrl: String? = null
)
