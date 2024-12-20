package com.jae464.presentation.main

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.core.util.Consumer
import androidx.navigation.compose.NavHost
import com.jae464.presentation.AppState
import com.jae464.presentation.LocalActivity
import com.jae464.presentation.detail.navigation.recipeDetailNavGraph
import com.jae464.presentation.contest.navigation.contestHistoryNavGraph
import com.jae464.presentation.home.navigation.homeNavGraph
import com.jae464.presentation.mypage.navigation.myPageNavGraph
import com.jae464.presentation.register.navigation.recipeRegisterNavGraph
import com.jae464.presentation.bookmark.navigation.bookMarkNavGraph
import com.jae464.presentation.contestdetail.navigation.contestDetailNavGraph
import com.jae464.presentation.detail.navigation.navigateRecipeDetail
import com.jae464.presentation.login.navigation.loginNavGraph
import com.jae464.presentation.mypage.navigation.profileEditNavGraph
import com.jae464.presentation.search.navigation.recipeSearchNavGraph
import com.jae464.presentation.splash.navigation.splashNavGraph
import com.jae464.presentation.util.StateHandleKey

@Composable
fun MainNavHost(
    appState: AppState,
    paddingValues: PaddingValues,
    onShowSnackBar: suspend (String, String?) -> Unit
) {
    val activity = LocalActivity.current

    val recipeId = activity.intent.getLongExtra("recipeId", -1)

    LaunchedEffect(recipeId) {
        if (recipeId != -1L) {
            appState.navigateToRecipeDetail(recipeId)
        }
    }

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
            onClickSearch = { appState.navigateToRecipeSearch(it) },
            onClickRecipe = { appState.navigateToRecipeDetail(it) },
            onClickRegister = { appState.navigateToRecipeRegister() },
        )
        contestHistoryNavGraph(
            padding = paddingValues,
            onClickContest = { id, title -> appState.navigateToContestDetail(id, title) }
        )
        bookMarkNavGraph(
            padding = paddingValues,
            onClickRecipe = { appState.navigateToRecipeDetail(it) }
        )
        myPageNavGraph(
            padding = paddingValues,
            onNavigateProfileEdit = { nickname, profileImageUrl -> appState.navigateToProfileEdit(nickname, profileImageUrl) }
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
        recipeSearchNavGraph(
            onBackClick = { appState.popBackStack() },
            onRecipeClick = { appState.navigateToRecipeDetail(it) }
        )
        contestDetailNavGraph(
            onBackClick = { appState.popBackStack() },
            onClickRecipe = { appState.navigateToRecipeDetail(it) }
        )
        profileEditNavGraph(
            onBackClick = { appState.popBackStack() },
            onNavigateToMyPage = {
                appState.navController.previousBackStackEntry?.savedStateHandle?.set(StateHandleKey.IS_REFRESH_KEY, true)
                appState.popBackStack()
            }
        )
    }
}

