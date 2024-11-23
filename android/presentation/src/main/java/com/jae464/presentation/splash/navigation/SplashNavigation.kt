package com.jae464.presentation.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jae464.presentation.login.LoginRoute
import com.jae464.presentation.main.Route
import com.jae464.presentation.splash.SplashRoute

fun NavGraphBuilder.splashNavGraph(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    composable<Route.Splash>() { navBackStackEntry ->
        SplashRoute(
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToHome = onNavigateToHome
        )
    }
}