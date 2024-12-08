package com.jae464.presentation.search

sealed interface SearchIntent {
    data class UpdateKeyword(val keyword: String) : SearchIntent
    data object FetchRecipePreviews : SearchIntent
}