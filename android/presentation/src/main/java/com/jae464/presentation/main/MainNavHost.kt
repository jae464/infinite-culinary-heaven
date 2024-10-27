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
import com.jae464.presentation.scrap.navigation.scrapNavGraph

@Composable
fun MainNavHost(
    appState: AppState,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = appState.navController,
        startDestination = appState.startDestination
    ) {
        homeNavGraph(
            padding = paddingValues,
            onClickRecipe = { appState.navigateToRecipeDetail(it) },
            onClickRegister = { appState.navigateToRecipeRegister() }
        )
        contestHistoryNavGraph(
            padding = paddingValues
        )
        scrapNavGraph(
            padding = paddingValues
        )
        myPageNavGraph(
            padding = paddingValues
        )
        recipeDetailNavGraph(
            onBackClick = { appState.popBackStack() }
        )
        recipeRegisterNavGraph(
            onBackClick = { appState.popBackStack() }
        )
    }
}