package com.jae464.presentation.register

sealed interface RecipeRegisterEvent {
    data class ShowToast(val message: String) : RecipeRegisterEvent
    data object RegisterSuccess : RecipeRegisterEvent
    data object RegisterFailure : RecipeRegisterEvent
}