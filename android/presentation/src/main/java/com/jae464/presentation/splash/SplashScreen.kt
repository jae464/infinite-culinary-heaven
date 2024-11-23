package com.jae464.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jae464.presentation.ui.theme.Green10
import com.jae464.presentation.ui.theme.Green5
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        // todo login 상태 여부 확인. 일단 로그인 안되어있는걸로 가정
        delay(1000)
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Green5,
                        Green10,
                    )
                )
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(text = "요리맛짱", fontSize = 48.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

