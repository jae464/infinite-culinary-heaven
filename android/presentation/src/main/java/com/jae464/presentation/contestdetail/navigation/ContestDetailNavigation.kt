package com.jae464.presentation.contestdetail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jae464.presentation.contestdetail.ContestDetailRoute
import com.jae464.presentation.main.Route

fun NavController.navigateContestDetail(contestId: Long) {
    navigate(Route.ContestDetail(contestId))
}

fun NavGraphBuilder.contestDetailNavGraph(
    onBackClick: () -> Unit,
    onClickRecipe: (Long) -> Unit
) {
    composable<Route.ContestDetail>(
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
        val contestId = navBackStackEntry.toRoute<Route.ContestDetail>().contestId
        ContestDetailRoute(contestId = contestId, onBackClick = onBackClick, onClickRecipe = onClickRecipe)
    }


}