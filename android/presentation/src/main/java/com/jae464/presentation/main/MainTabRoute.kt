package com.jae464.presentation.main

import kotlinx.serialization.Serializable

sealed interface Route {
    val name: String

    @Serializable
    data class RecipeDetail(val recipeId: Long): Route {
        override val name: String = "recipe_detail"
    }

    @Serializable
    data class RecipeRegister(val recipeId: Long?): Route {
        override val name: String = "recipe_register"
    }

    @Serializable
    data object Splash : Route {
        override val name: String = "splash"
    }

    @Serializable
    data object Login : Route {
        override val name: String = "login"
    }

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
    data object BookMark: MainTabRoute {
        override val name: String = "book_mark"
    }

    @Serializable
    data object MyPage: MainTabRoute {
        override val name: String = "my_page"
    }
}