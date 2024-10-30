package com.jae464.presentation.mypage.navigation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.main.MainTabRoute
import com.jae464.presentation.mypage.MyPageRoute
import com.jae464.presentation.util.navigation.getMainTabDirection

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(MainTabRoute.MyPage, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues
) {
    composable<MainTabRoute.MyPage>(
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
        MyPageRoute(padding = padding)
    }
}