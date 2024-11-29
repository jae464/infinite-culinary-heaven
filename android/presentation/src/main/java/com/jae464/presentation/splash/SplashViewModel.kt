package com.jae464.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
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
                    _event.emit(SplashEvent.AutoLoginSuccess)
                }.onFailure {
                    Log.d("SplashViewModel", "refreshToken fail ${it.message}")
                    _event.emit(SplashEvent.AutoLoginFailed)
                }
        }
    }

}