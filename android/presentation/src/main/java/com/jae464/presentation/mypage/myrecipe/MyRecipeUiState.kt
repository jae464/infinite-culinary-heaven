package com.jae464.presentation.mypage.myrecipe

import com.jae464.domain.model.RecipePreview

data class MyRecipeUiState(
    val recipes: List<RecipePreview> = emptyList(),
    val isLoading: Boolean = false
)
