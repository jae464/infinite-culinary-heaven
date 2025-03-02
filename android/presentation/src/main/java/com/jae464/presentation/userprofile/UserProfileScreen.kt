package com.jae464.presentation.userprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.ui.theme.Gray20

@Composable
fun UserProfileRoute(
    userId: Long,
    onBackClick: () -> Unit,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    UserProfileScreen(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Gray20
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HeavenTopAppBar(
            title = "프로필",
            navigationIcon = Icons.Default.ArrowBack,
            useNavigationIcon = true,
            onNavigationClick = onBackClick,
        )
    }
}