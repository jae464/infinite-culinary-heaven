package com.jae464.presentation.home

import com.jae464.domain.model.Contest
import com.jae464.domain.model.RecipePreview

data class HomeUiState(
    val recipePreviews: List<RecipePreview> = emptyList(),
    val currentContest: Contest? = null,
    val isLoading: Boolean = false,
)