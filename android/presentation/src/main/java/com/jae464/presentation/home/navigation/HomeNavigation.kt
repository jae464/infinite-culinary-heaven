package com.jae464.presentation.home.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.home.HomeRoute
import com.jae464.presentation.main.MainTabRoute
import com.jae464.presentation.util.StateHandleKey
import com.jae464.presentation.util.navigation.getMainTabDirection

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(MainTabRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onClickSearch: (Long?) -> Unit,
    onClickRecipe: (Long) -> Unit,
    onClickRegister: () -> Unit,
) {
    composable<MainTabRoute.Home>(
        enterTransition = {
            val direction = getMainTabDirection(initialState.destination, targetState.destination)
            if (direction == null) {
                null
            } else {
                slideIntoContainer(
                    towards = direction,
                    animationSpec = tween(200)
                )
            }
        },
        exitTransition = {
            val direction = getMainTabDirection(initialState.destination, targetState.destination)
            if (direction == null) {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            } else {
                slideOutOfContainer(
                    towards = direction,
                    animationSpec = tween(200)
                )
            }
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        },
        popExitTransition = {
            null
        }
    ) {
        val isRefresh = it.savedStateHandle.getStateFlow(StateHandleKey.IS_REFRESH_KEY, false).collectAsState()

        LaunchedEffect(Unit) {
            if (isRefresh.value) {
                it.savedStateHandle.remove<Boolean>(StateHandleKey.IS_REFRESH_KEY)
            }
        }

        HomeRoute(
            padding = padding,
            onClickSearch = onClickSearch,
            onClickRecipe = onClickRecipe,
            onClickRegister = onClickRegister,
            isRefresh = isRefresh.value
        )
    }
}