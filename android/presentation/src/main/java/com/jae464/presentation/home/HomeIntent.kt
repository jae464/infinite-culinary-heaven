package com.jae464.presentation.home

sealed interface HomeIntent {
    data object FetchRecipePreviews : HomeIntent
}