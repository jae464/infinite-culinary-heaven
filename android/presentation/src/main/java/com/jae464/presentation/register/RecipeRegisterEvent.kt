package com.jae464.presentation.register

sealed interface RecipeRegisterEvent {
    data object RegisterSuccess : RecipeRegisterEvent
    data object RegisterFailure : RecipeRegisterEvent
}