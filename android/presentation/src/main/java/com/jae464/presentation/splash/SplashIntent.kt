package com.jae464.presentation.splash

sealed interface SplashIntent {

    data object TryAutoLogin: SplashIntent

}