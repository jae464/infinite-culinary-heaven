package com.jae464.presentation.login

sealed interface LoginIntent {
    data object KakaoLogin : LoginIntent
    data object Logout : LoginIntent

}