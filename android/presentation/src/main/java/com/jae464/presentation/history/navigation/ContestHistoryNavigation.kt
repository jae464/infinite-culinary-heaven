package com.jae464.presentation.history.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.history.ContestHistoryRoute


import com.jae464.presentation.main.MainTabRoute

fun NavController.navigateContestHistory(navOptions: NavOptions) {
    navigate(MainTabRoute.ContestHistory, navOptions)
}

fun NavGraphBuilder.contestHistoryNavGraph(
    padding: PaddingValues
) {
    composable<MainTabRoute.ContestHistory> {
        ContestHistoryRoute(padding = padding)
    }
}