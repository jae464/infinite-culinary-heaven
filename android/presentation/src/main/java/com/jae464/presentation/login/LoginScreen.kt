package com.jae464.presentation.login

import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.jae464.presentation.BuildConfig
import com.jae464.presentation.R
import com.jae464.presentation.ui.theme.Green10
import com.jae464.presentation.ui.theme.Green5
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kakao_login_large_wide),
                contentDescription = "kakao login",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        kakaoLogin(
                            context = context,
                            onLoginSuccess = { onIntent(LoginIntent.KakaoLogin(it.accessToken)) }
                        )
                    },
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.google_login),
                contentDescription = "google login",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        googleLogin(
                            context = context,
                            onLoginSuccess = { onIntent(LoginIntent.GoogleLogin(it))}
                        )
                    },
                contentScale = ContentScale.Crop
            )
        }
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

fun googleLogin(context: Context, onLoginSuccess: (String) -> Unit) {

    val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(
        BuildConfig.GOOGLE_CLIENT_ID
    ).build()

    Log.d("LoginScreen", signInWithGoogleOption.toString())
    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val result = CredentialManager.create(context)
                .getCredential(context, request)
            val googleIdToken = GoogleIdTokenCredential
                .createFrom(result.credential.data)
                .idToken

            Log.d("LoginScreen", googleIdToken.toString())
            onLoginSuccess(googleIdToken)

        } catch (e: Exception) {
            Log.e("LoginScreen", "googleLogin: $e")
        }
    }


}