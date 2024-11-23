package com.jae464.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.UserApi
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
            is LoginIntent.KakaoLogin -> kakaoLogin()
            is LoginIntent.Logout -> TODO()
        }
    }

    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error -> }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error -> }
        }
    }

}