package com.jae464.presentation.detail

sealed interface RecipeDetailIntent {
    data class FetchRecipe(val recipeId: Long) : RecipeDetailIntent
    data class DeleteRecipe(val recipeId: Long) : RecipeDetailIntent
    data class AddBookMark(val recipeId: Long) : RecipeDetailIntent
    data class DeleteBookMark(val recipeId: Long) : RecipeDetailIntent
    data class LikeRecipe(val recipeId: Long) : RecipeDetailIntent
    data class UnlikeRecipe(val recipeId: Long) : RecipeDetailIntent
}
