package com.jae464.presentation.mypage

sealed interface MyLikesIntent {
    data object FetchMyLikesRecipePreviews : MyLikesIntent
}