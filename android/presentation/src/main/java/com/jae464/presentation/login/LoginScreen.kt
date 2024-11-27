package com.jae464.presentation.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jae464.presentation.R
import com.jae464.presentation.ui.theme.Green10
import com.jae464.presentation.ui.theme.Green5
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

@Composable
fun LoginRoute(
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val event = viewModel.uiEvent

    LaunchedEffect(Unit) {
        event.collect {
            when (it) {
                is LoginEvent.LoginSuccess -> onNavigateToHome()
                is LoginEvent.LoginFailed -> {
                    Log.d("LoginRoute", "LoginFailed")
                }
            }
        }
    }

    LoginScreen(
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun LoginScreen(
    onIntent: (LoginIntent) -> Unit = {}
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Green5,
                        Green10,
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_login_large_wide),
            contentDescription = "kakao login",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clickable {
                    kakaoLogin(
                        context = context,
                        onLoginSuccess = { onIntent(LoginIntent.KakaoLogin(it.accessToken)) }
                    )
                },
            contentScale = ContentScale.Crop
        )
    }
}

fun kakaoLogin(context: Context, onLoginSuccess: (OAuthToken) -> Unit) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e("LoginViewModel", "kakaoLogin: $error")
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    Log.d("LoginViewModel", "kakaoLogin: $token")
                    Log.e("LoginViewModel", "kakaoLogin: $error")

                    if (error != null) {
                        Log.e("LoginViewModel", "kakaoLogin: $error")
                    }
                    else if (token != null) {
                        onLoginSuccess(token)
                    }
                }
            }
            else if (token != null) {
                Log.d("LoginViewModel", "kakaoLogin: $token")
                onLoginSuccess(token)
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            Log.d("LoginViewModel", "kakaoLogin: $token")
            Log.e("LoginViewModel", "kakaoLogin: $error")
            if (error != null) {
                Log.e("LoginViewModel", "kakaoLogin: $error")
            }
            else if (token != null) {
                onLoginSuccess(token)
            }

        }
    }
}