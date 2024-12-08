package com.jae464.presentation.search.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jae464.presentation.main.Route
import com.jae464.presentation.search.SearchRoute

fun NavController.navigateRecipeSearch(contestId: Long?) {
    navigate(Route.RecipeSearch(contestId))
}

fun NavGraphBuilder.recipeSearchNavGraph(
    onBackClick: () -> Unit,
    onRecipeClick: (Long) -> Unit
) {
    composable<Route.RecipeSearch>(
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
        val contestId = navBackStackEntry.toRoute<Route.RecipeSearch>().contestId
        SearchRoute(
            contestId = contestId,
            onBackClick = onBackClick,
            onRecipeClick = onRecipeClick
        )
    }

}