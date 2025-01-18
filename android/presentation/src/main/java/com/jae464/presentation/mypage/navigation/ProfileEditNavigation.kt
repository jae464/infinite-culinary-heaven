package com.jae464.presentation.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.main.Route
import com.jae464.presentation.mypage.editprofile.ProfileEditRoute

fun NavController.navigateProfileEdit(nickname: String, profileImageUrl: String?, navOptions: NavOptions? = null) {
    navigate(Route.ProfileEdit(nickname, profileImageUrl), navOptions)
}

fun NavGraphBuilder.profileEditNavGraph(
    onBackClick: () -> Unit,
    onNavigateToMyPage: () -> Unit
) {
    composable<Route.ProfileEdit>(
    ) { navBackStackEntry ->

        ProfileEditRoute(
            onBackClick = onBackClick,
            onProfileEditSuccess = onNavigateToMyPage,
        )

    }
}