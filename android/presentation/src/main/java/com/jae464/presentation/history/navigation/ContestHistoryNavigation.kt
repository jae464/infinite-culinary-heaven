package com.jae464.presentation.history.navigation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.history.ContestHistoryRoute


import com.jae464.presentation.main.MainTabRoute
import com.jae464.presentation.util.navigation.getMainTabDirection

fun NavController.navigateContestHistory(navOptions: NavOptions) {
    navigate(MainTabRoute.ContestHistory, navOptions)
}

fun NavGraphBuilder.contestHistoryNavGraph(
    padding: PaddingValues
) {
    composable<MainTabRoute.ContestHistory>(
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
                null
            } else {
                slideOutOfContainer(
                    towards = direction,
                    animationSpec = tween(200)
                )
            }
        },
    ) {
        ContestHistoryRoute(padding = padding)
    }
}