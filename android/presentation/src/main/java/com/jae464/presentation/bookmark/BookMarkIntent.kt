package com.jae464.presentation.bookmark

sealed interface BookMarkIntent {
    data object InitBookMarkedRecipes: BookMarkIntent
    data object FetchBookMarkedRecipes : BookMarkIntent
}