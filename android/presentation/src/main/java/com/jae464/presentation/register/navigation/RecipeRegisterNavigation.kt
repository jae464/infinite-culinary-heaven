package com.jae464.presentation.register.navigation

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
    onBackClick: () -> Unit
) {
    composable<Route.RecipeRegister> { navBackStackEntry ->
        val recipeId = navBackStackEntry.toRoute<Route.RecipeRegister>().recipeId
        RecipeRegisterRoute(recipeId = recipeId, onBackClick = onBackClick)
    }

}