package com.jae464.presentation.register

import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Step

sealed interface RecipeRegisterIntent {
    data class SetThumbnailImage(val thumbnailImage: String?) : RecipeRegisterIntent
    data class SetTitle(val title: String) : RecipeRegisterIntent
    data class SetDescription(val description: String) : RecipeRegisterIntent
    data class AddIngredient(val ingredient: Ingredient) : RecipeRegisterIntent
    data class AddStep(val step: Step) : RecipeRegisterIntent
    data class RemoveIngredient(val ingredient: Ingredient) : RecipeRegisterIntent
    data class RemoveStep(val step: Step) : RecipeRegisterIntent
    data object Submit : RecipeRegisterIntent
}