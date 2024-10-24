package com.jae464.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.jae464.presentation.detail.navigation.navigateRecipeDetail
import com.jae464.presentation.history.navigation.navigateContestHistory
import com.jae464.presentation.home.navigation.navigateHome
import com.jae464.presentation.main.MainTab
import com.jae464.presentation.mypage.navigation.navigateMyPage
import com.jae464.presentation.scrap.navigation.navigateScrap

@Stable
class AppState(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTab.HOME.route

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateHome(navOptions)
            MainTab.HISTORY -> navController.navigateContestHistory(navOptions)
            MainTab.SCRAP -> navController.navigateScrap(navOptions)
            MainTab.MYPAGE -> navController.navigateMyPage(navOptions)
        }
    }

    fun navigateToRecipeDetail(recipeId: Long) {
        navController.navigateRecipeDetail(recipeId)
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }

}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(
    navController
) {
    AppState(navController = navController)
}