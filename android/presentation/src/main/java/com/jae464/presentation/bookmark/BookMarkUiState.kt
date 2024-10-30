package com.jae464.presentation.bookmark

import com.jae464.domain.model.RecipePreview

data class BookMarkUiState(
    val bookMarkedRecipes: List<RecipePreview> = emptyList(),
)
