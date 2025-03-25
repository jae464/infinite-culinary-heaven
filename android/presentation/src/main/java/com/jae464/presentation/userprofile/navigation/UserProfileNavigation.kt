package com.jae464.presentation.userprofile.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jae464.presentation.main.Route
import com.jae464.presentation.userprofile.UserProfileRoute

fun NavController.navigateUserProfile(userId: Long) {
    navigate(Route.UserProfile(userId))
}

fun NavGraphBuilder.userProfileNavGraph(
    onClickRecipe: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    composable<Route.UserProfile>(
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

        val userId = navBackStackEntry.toRoute<Route.UserProfile>().userId

        UserProfileRoute(
            userId = userId,
            onClickRecipe = onClickRecipe,
            onBackClick = onBackClick
        )

    }
}