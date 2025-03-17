package com.jae464.presentation.login

import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        event.collect {
            when (it) {
                is LoginEvent.LoginSuccess -> onNavigateToHome()
                is LoginEvent.LoginFailed -> {
                    Toast.makeText(context, "현재 서버가 점검중이에요.", Toast.LENGTH_SHORT).show()
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color(0xFFFEE500), shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        googleLogin(
                            context = context,
                            onLoginSuccess = { onIntent(LoginIntent.GoogleLogin(it))}
                        )
                    }
                    .padding(horizontal = 18.dp, vertical = 16.dp),
            ) {
                Image(
                    modifier = Modifier.align(Alignment.CenterStart),
                    painter = painterResource(id = R.drawable.ic_kakao_logo),
                    contentDescription = "kakao login",
                    contentScale = ContentScale.FillBounds,
                )
                Text(
                    text = "카카오 로그인",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        googleLogin(
                            context = context,
                            onLoginSuccess = { onIntent(LoginIntent.GoogleLogin(it))}
                        )
                    }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Image(
                    modifier = Modifier.align(Alignment.CenterStart),
                    painter = painterResource(id = R.drawable.btn_google),
                    contentDescription = "google login",
                    contentScale = ContentScale.FillBounds,
                )
                Text(
                    text = "구글 로그인",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

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