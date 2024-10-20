package com.jae464.presentation.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.history.ContestHistoryScreen
import com.jae464.presentation.main.MainTabRoute
import com.jae464.presentation.mypage.MyPageScreen

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(MainTabRoute.MyPage, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues
) {
    composable<MainTabRoute.MyPage> {
        MyPageScreen(padding = padding)
    }
}