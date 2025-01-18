package com.jae464.presentation.mypage.myrecipe

sealed interface MyRecipeIntent {
    data object FetchMyRecipePreviews : MyRecipeIntent
}