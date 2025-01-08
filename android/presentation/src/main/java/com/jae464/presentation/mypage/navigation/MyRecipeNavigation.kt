package com.jae464.presentation.mypage.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
    onBackClick: () -> Unit,
    onClickRecipe: (Long) -> Unit
) {
    composable<Route.MyRecipe>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )
        },
        exitTransition = {
            null
        },
        popEnterTransition = {
            null
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        }
    ) {
        MyRecipeRoute(
            onBackClick = onBackClick,
            onClickRecipe = onClickRecipe
        )
    }
}