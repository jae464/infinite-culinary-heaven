package com.jae464.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {viewModel.handleIntent(LoginIntent.KakaoLogin)}, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text("로그인")
        }
    }
}