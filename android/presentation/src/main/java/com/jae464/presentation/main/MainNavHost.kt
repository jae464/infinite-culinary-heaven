package com.jae464.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.jae464.presentation.AppState
import com.jae464.presentation.detail.navigation.recipeDetailNavGraph
import com.jae464.presentation.history.navigation.contestHistoryNavGraph
import com.jae464.presentation.home.navigation.homeNavGraph
import com.jae464.presentation.mypage.navigation.myPageNavGraph
import com.jae464.presentation.register.navigation.recipeRegisterNavGraph
import com.jae464.presentation.bookmark.navigation.bookMarkNavGraph
import com.jae464.presentation.login.navigation.loginNavGraph
import com.jae464.presentation.splash.navigation.splashNavGraph
import com.jae464.presentation.util.navigation.StateHandleKey

@Composable
fun MainNavHost(
    appState: AppState,
    paddingValues: PaddingValues,
    onShowSnackBar: suspend (String, String?) -> Unit
) {
    NavHost(
        navController = appState.navController,
        startDestination = appState.startDestination,
    ) {
        splashNavGraph(
            onNavigateToHome = { appState.navigateSplashToHome() },
            onNavigateToLogin = { appState.navigateToLogin() }
        )
        loginNavGraph(
            onNavigateToHome = { appState.navigateLoginToHome() }
        )
        homeNavGraph(
            padding = paddingValues,
            onClickRecipe = { appState.navigateToRecipeDetail(it) },
            onClickRegister = { appState.navigateToRecipeRegister() },
        )
        contestHistoryNavGraph(
            padding = paddingValues
        )
        bookMarkNavGraph(
            padding = paddingValues,
            onClickRecipe = { appState.navigateToRecipeDetail(it) }
        )
        myPageNavGraph(
            padding = paddingValues
        )
        recipeDetailNavGraph(
            onBackClick = { appState.popBackStack() },
            onNavigateToHome = {
                appState.navController.previousBackStackEntry?.savedStateHandle?.set(StateHandleKey.IS_REFRESH_KEY, true)
                appState.popBackStack()
            },
        )
        recipeRegisterNavGraph(
            onBackClick = { appState.popBackStack() },
            onNavigateToHome = {
                appState.navController.previousBackStackEntry?.savedStateHandle?.set(StateHandleKey.IS_REFRESH_KEY, true)
                appState.popBackStack()
            },
            onShowSnackBar = onShowSnackBar
        )
    }
}

