package com.jae464.presentation.mypage.mylikes

sealed interface MyLikesIntent {
    data object FetchMyLikesRecipePreviews : MyLikesIntent
}