package com.jae464.presentation.detail

sealed interface RecipeDetailEvent {
    data object DeleteSuccess : RecipeDetailEvent
    data object AddBookMarkSuccess : RecipeDetailEvent
    data object DeleteBookMarkSuccess : RecipeDetailEvent
    data object LikeSuccess : RecipeDetailEvent
    data object UnlikeSuccess : RecipeDetailEvent
}
