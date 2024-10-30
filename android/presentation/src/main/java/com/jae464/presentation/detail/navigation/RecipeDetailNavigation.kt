package com.jae464.presentation.detail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jae464.presentation.detail.RecipeDetailRoute
import com.jae464.presentation.main.Route

fun NavController.navigateRecipeDetail(recipeId: Long) {
    navigate(Route.RecipeDetail(recipeId))
}

fun NavGraphBuilder.recipeDetailNavGraph(
    onBackClick: () -> Unit
) {
    composable<Route.RecipeDetail>(
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
    ) { navBackStackEntry ->
        val recipeId = navBackStackEntry.toRoute<Route.RecipeDetail>().recipeId
        RecipeDetailRoute(recipeId = recipeId, onBackClick = onBackClick)
    }
}