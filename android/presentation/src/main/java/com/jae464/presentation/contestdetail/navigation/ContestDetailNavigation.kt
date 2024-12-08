package com.jae464.presentation.contestdetail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jae464.presentation.contestdetail.ContestDetailRoute
import com.jae464.presentation.main.Route

fun NavController.navigateContestDetail(contestId: Long, contestTitle: String) {
    navigate(Route.ContestDetail(contestId, contestTitle))
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
        val contestTitle = navBackStackEntry.toRoute<Route.ContestDetail>().contestTitle
        ContestDetailRoute(contestId = contestId, contestTitle = contestTitle, onBackClick = onBackClick, onClickRecipe = onClickRecipe)
    }


}