package com.jae464.presentation.mypage

import com.jae464.domain.model.RecipePreview

data class MyRecipeUiState(
    val recipes: List<RecipePreview> = emptyList(),
    val isLoading: Boolean = false
)
