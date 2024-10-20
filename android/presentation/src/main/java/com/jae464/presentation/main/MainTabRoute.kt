package com.jae464.presentation.main

import kotlinx.serialization.Serializable

sealed interface Route {
    val name: String
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home: MainTabRoute {
        override val name: String = "home"
    }

    @Serializable
    data object ContestHistory: MainTabRoute {
        override val name: String = "contest_history"
    }

    @Serializable
    data object Scrap: MainTabRoute {
        override val name: String = "scrap"
    }

    @Serializable
    data object MyPage: MainTabRoute {
        override val name: String = "my_page"
    }
}