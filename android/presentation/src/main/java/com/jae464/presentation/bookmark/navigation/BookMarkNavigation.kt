package com.jae464.presentation.bookmark.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.main.MainTabRoute
import com.jae464.presentation.bookmark.BookMarkRoute
import com.jae464.presentation.util.navigation.getMainTabDirection

fun NavController.navigateBookMark(navOptions: NavOptions) {
    navigate(MainTabRoute.BookMark, navOptions)
}

fun NavGraphBuilder.bookMarkNavGraph(
    padding: PaddingValues,
    onClickRecipe: (Long) -> Unit
) {
    composable<MainTabRoute.BookMark>(
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
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        }
    ) {
        BookMarkRoute(
            padding = padding,
            onClickRecipe = onClickRecipe
        )
    }
}