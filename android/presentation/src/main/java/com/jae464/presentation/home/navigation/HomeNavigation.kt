package com.jae464.presentation.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.home.HomeRoute
import com.jae464.presentation.main.MainTabRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(MainTabRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onClickRecipe: (Long) -> Unit,
    onClickRegister: () -> Unit
) {
    composable<MainTabRoute.Home> {
        HomeRoute(
            padding = padding,
            onClickRecipe = onClickRecipe,
            onClickRegister = onClickRegister
        )
    }
}