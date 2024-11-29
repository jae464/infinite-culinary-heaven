package com.jae464.presentation.splash

sealed interface SplashEvent {
    data object AutoLoginSuccess : SplashEvent
    data object AutoLoginFailed : SplashEvent

}