package com.jae464.presentation.contestdetail

import com.jae464.domain.model.RecipePreview

data class ContestDetailUiState(
    val recipePreviews: List<RecipePreview> = emptyList(),
)
