package com.jae464.presentation.search

import com.jae464.domain.model.RecipePreview

data class SearchUiState(
    val recipePreviews: List<RecipePreview> = emptyList(),
    val isLoading: Boolean = false,
)
