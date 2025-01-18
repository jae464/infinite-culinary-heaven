package com.jae464.presentation.mypage.mylikes

import com.jae464.domain.model.RecipePreview

data class MyLikesUiState(
    val recipes: List<RecipePreview> = emptyList(),
    val isLoading: Boolean = false
)
