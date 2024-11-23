package com.jae464.presentation.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jae464.presentation.login.LoginRoute
import com.jae464.presentation.main.Route

fun NavController.navigateLogin(navOptions: NavOptions) {
    navigate(Route.Login, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    onLoginSuccess: () -> Unit
) {
    composable<Route.Login>() { navBackStackEntry ->
        LoginRoute(
            onLoginSuccess = onLoginSuccess
        )
    }
}