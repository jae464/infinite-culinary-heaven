package com.jae464.presentation.scrap.navigation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.main.MainTabRoute
import com.jae464.presentation.mypage.MyPageScreen
import com.jae464.presentation.scrap.ScrapRoute
import com.jae464.presentation.scrap.ScrapScreen
import com.jae464.presentation.util.navigation.getMainTabDirection

fun NavController.navigateScrap(navOptions: NavOptions) {
    navigate(MainTabRoute.Scrap, navOptions)
}

fun NavGraphBuilder.scrapNavGraph(
    padding: PaddingValues
) {
    composable<MainTabRoute.Scrap>(
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
        ScrapRoute(padding = padding)
    }
}