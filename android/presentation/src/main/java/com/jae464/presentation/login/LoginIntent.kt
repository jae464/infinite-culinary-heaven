package com.jae464.presentation.login

sealed interface LoginIntent {
    data class KakaoLogin(val accessToken: String) : LoginIntent
    data object Logout : LoginIntent

}