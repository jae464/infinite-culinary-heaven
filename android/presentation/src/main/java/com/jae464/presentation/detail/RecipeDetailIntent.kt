package com.jae464.presentation.detail

sealed interface RecipeDetailIntent {
    data class FetchRecipe(val recipeId: Long) : RecipeDetailIntent
}
