package com.jae464.presentation.detail.navigation

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
    composable<Route.RecipeDetail> { navBackStackEntry ->
        val recipeId = navBackStackEntry.toRoute<Route.RecipeDetail>().recipeId
        RecipeDetailRoute(recipeId = recipeId, onBackClick = onBackClick)
    }
}