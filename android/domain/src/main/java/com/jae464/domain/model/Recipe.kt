package com.jae464.domain.model

data class Recipe(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val description: String,
    val score: Float,
    val author: String,
    // 이 아래 필드들은 수정 필요
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val cookTime: String,
    val servingSize: String,
    val videoUrl: String? = null
)

data class Ingredient(
    val name: String,
    val quantity: String
)
