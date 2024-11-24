package com.jae464.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.KakaoLogin -> TODO()
            is LoginIntent.Logout -> TODO()
        }
    }

}