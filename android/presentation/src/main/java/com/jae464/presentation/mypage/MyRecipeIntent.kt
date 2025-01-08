package com.jae464.presentation.mypage

sealed interface MyRecipeIntent {
    data object FetchMyRecipePreviews : MyRecipeIntent
}