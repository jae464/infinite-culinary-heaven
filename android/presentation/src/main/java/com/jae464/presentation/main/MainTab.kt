package com.jae464.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainTab(
    val iconImageVector: ImageVector,
    val description: String,
    val route: MainTabRoute
) {
    HOME(
        iconImageVector = Icons.Default.Home,
        description = "홈",
        MainTabRoute.Home
    ),
    HISTORY(
        iconImageVector = Icons.Default.History,
        description = "지난대회",
        MainTabRoute.ContestHistory
    ),
    SCRAP(
        iconImageVector = Icons.Default.BookmarkBorder,
        description = "스크랩",
        route = MainTabRoute.BookMark
    ),
    MYPAGE(
      iconImageVector = Icons.Default.Person,
        description = "마이페이지",
        route = MainTabRoute.MyPage
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }

}

