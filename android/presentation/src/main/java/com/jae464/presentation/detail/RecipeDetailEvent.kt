package com.jae464.presentation.detail

sealed interface RecipeDetailEvent {
    data object DeleteSuccess : RecipeDetailEvent
}
