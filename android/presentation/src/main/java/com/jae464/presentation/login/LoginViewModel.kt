package com.jae464.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.jae464.domain.repository.AuthRepository
import com.jae464.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiEvent = MutableSharedFlow<LoginEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.KakaoLogin -> {
                // todo 서버에 login 요청
                Log.d("LoginViewModel" , intent.accessToken)
                viewModelScope.launch {
                    authRepository.login(intent.accessToken, "kakao")
                        .onSuccess {
                            Log.d("LoginViewModel", "login success")
                            val deviceToken = FirebaseMessaging.getInstance().token.await()
                            userRepository.updateDeviceToken(deviceToken)
                            _uiEvent.emit(LoginEvent.LoginSuccess)
                        }
                        .onFailure {
                            _uiEvent.emit(LoginEvent.LoginFailed)
                        }
                }
            }
            is LoginIntent.Logout -> TODO()
        }
    }

}