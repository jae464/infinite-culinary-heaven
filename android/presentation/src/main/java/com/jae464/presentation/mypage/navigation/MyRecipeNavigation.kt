package com.jae464.presentation.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.main.Route
import com.jae464.presentation.mypage.MyRecipeRoute

fun NavController.navigateMyRecipe(navOptions: NavOptions? = null) {
    navigate(Route.MyRecipe, navOptions)
}

fun NavGraphBuilder.myRecipeNavGraph(
    onBackClick: () -> Unit
) {
    composable<Route.MyRecipe> {
        MyRecipeRoute(
            onBackClick = onBackClick
        )
    }
}