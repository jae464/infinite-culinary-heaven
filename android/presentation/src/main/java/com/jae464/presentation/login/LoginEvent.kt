package com.jae464.presentation.login

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
}