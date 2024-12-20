package com.jae464.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.jae464.domain.repository.AuthRepository
import com.jae464.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _event = MutableSharedFlow<SplashEvent>()
    val event: SharedFlow<SplashEvent> = _event.asSharedFlow()

    fun handleIntent(intent: SplashIntent) {
        when(intent) {
            SplashIntent.TryAutoLogin -> {
                tryAutoLogin()
            }
        }
    }

    private fun tryAutoLogin() {
        viewModelScope.launch {
            authRepository.refreshToken()
                .onSuccess {
                    Log.d("SplashViewModel", "refreshToken success")
                    val deviceToken = FirebaseMessaging.getInstance().token.await()
                    userRepository.updateDeviceToken(deviceToken)
                    _event.emit(SplashEvent.AutoLoginSuccess)
                }.onFailure {
                    Log.d("SplashViewModel", "refreshToken fail ${it.message}")
                    _event.emit(SplashEvent.AutoLoginFailed)
                }
        }
    }

}