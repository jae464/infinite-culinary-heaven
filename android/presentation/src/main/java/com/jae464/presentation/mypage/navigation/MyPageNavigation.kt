package com.jae464.presentation.mypage.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.main.MainTabRoute
import com.jae464.presentation.mypage.MyPageRoute
import com.jae464.presentation.util.StateHandleKey
import com.jae464.presentation.util.navigation.getMainTabDirection

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(MainTabRoute.MyPage, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues,
    onNavigateProfileEdit: (String, String?) -> Unit,
    onNavigateToMyRecipe: () -> Unit,
    onNavigateToMyLikes: () -> Unit,
    onNavigateToSetting: () -> Unit
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
    ) { navBackStackEntry ->
        val isRefresh =
            navBackStackEntry.savedStateHandle.getStateFlow(StateHandleKey.IS_REFRESH_KEY, false)
                .collectAsState()

        LaunchedEffect(Unit) {
            if (isRefresh.value) {
                navBackStackEntry.savedStateHandle.remove<Boolean>(StateHandleKey.IS_REFRESH_KEY)
            }
        }

        MyPageRoute(
            padding = padding,
            onClickEditProfile = onNavigateProfileEdit,
            onClickMyRecipe = onNavigateToMyRecipe,
            onClickMyLikes = onNavigateToMyLikes,
            onClickSetting = onNavigateToSetting,
            isRefresh = isRefresh.value
        )
    }
}