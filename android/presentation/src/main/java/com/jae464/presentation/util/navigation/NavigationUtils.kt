package com.jae464.presentation.util.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.jae464.presentation.main.MainTab

fun getMainTabDirection(
    startDestination: NavDestination,
    targetDestination: NavDestination
): AnimatedContentTransitionScope.SlideDirection? {
    val startRoute = startDestination.getRoute()
    val targetRoute = targetDestination.getRoute()

    if (startRoute != null && targetRoute != null) {
        val diff = targetRoute.ordinal - startRoute.ordinal
        return when {
            diff < 0 -> {
                AnimatedContentTransitionScope.SlideDirection.Right
            }

            else -> {
                AnimatedContentTransitionScope.SlideDirection.Left
            }
        }
    }
    return null
}

fun NavDestination.getRoute(): MainTab? {
    return MainTab.entries.find { tab -> this.hasRoute(tab.route::class) }
}
