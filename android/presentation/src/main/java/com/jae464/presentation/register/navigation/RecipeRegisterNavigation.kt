package com.jae464.presentation.register.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jae464.presentation.main.Route
import com.jae464.presentation.register.RecipeRegisterRoute

fun NavController.navigateRecipeRegister(recipeId: Long?) {
    navigate(Route.RecipeRegister(recipeId))
}

fun NavGraphBuilder.recipeRegisterNavGraph(
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    composable<Route.RecipeRegister>(
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
        val recipeId = navBackStackEntry.toRoute<Route.RecipeRegister>().recipeId
        RecipeRegisterRoute(recipeId = recipeId, onBackClick = onBackClick, onRegisterSuccess = onNavigateToHome)
    }

}