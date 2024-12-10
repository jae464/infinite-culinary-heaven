package com.jae464.presentation.detail

import com.jae464.domain.model.Comment
import com.jae464.domain.model.Recipe

data class RecipeDetailUiState(
    val recipe: Recipe? = null,
    val commentInput: String = "",
    val comments: List<Comment> = emptyList()
)
