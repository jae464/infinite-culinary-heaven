package com.jae464.presentation.register

import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Step

data class RecipeRegisterUiState(
    val thumbnailImage: String? = null,
    val title: String = "",
    val description: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<Step> = emptyList(),
)
