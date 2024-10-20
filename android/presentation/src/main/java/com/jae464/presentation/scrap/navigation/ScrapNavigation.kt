package com.jae464.presentation.scrap.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.main.MainTabRoute
import com.jae464.presentation.mypage.MyPageScreen
import com.jae464.presentation.scrap.ScrapScreen

fun NavController.navigateScrap(navOptions: NavOptions) {
    navigate(MainTabRoute.Scrap, navOptions)
}

fun NavGraphBuilder.scrapNavGraph(
    padding: PaddingValues
) {
    composable<MainTabRoute.Scrap> {
        ScrapScreen(padding = padding)
    }
}