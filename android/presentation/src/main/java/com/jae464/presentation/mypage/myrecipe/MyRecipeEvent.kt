package com.jae464.presentation.mypage.myrecipe

sealed interface MyRecipeEvent {
    data object FetchRecipeFailed : MyRecipeEvent

}