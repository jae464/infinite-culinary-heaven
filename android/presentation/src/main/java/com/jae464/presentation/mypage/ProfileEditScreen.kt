package com.jae464.presentation.mypage

import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.ui.theme.Gray20
import com.jae464.presentation.util.ImageConstants
import com.jae464.presentation.util.rememberGalleryLauncher
import com.jae464.presentation.util.rememberPermissionLauncher

@Composable
fun ProfileEditRoute(
    viewModel: ProfileEditViewModel = hiltViewModel(),
    onProfileEditSuccess: () -> Unit,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val event = viewModel.event
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        event.collect {
            when (it) {
                is ProfileEditEvent.UpdateProfileSuccess -> {
                    Toast.makeText(context, "프로필이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                    onProfileEditSuccess()
                }
            }
        }

    }

    ProfileEditScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    uiState: ProfileEditUiState,
    onIntent: (ProfileEditIntent) -> Unit = {},
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    val galleryLauncher = rememberGalleryLauncher {
        if (it != null) {
            onIntent(ProfileEditIntent.UpdateProfileImage(it.toString()))
        }
    }

    val permissionLauncher = rememberPermissionLauncher(
        onGranted = { galleryLauncher.launch("image/*") },
        onDenied = { Toast.makeText(context, "권한이 필요합니다.", Toast.LENGTH_SHORT).show() }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HeavenTopAppBar(
            title = "프로필 수정",
            navigationIcon = Icons.Default.Close,
            useNavigationIcon = true,
            onNavigationClick = onBackClick,
            actions = {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "save_profile",
                    modifier = Modifier.clickable {
                        onIntent(ProfileEditIntent.UpdateProfile)
                    }
                )
            }
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 0.5.dp
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = uiState.profileImageUrl ?: ImageConstants.DEFAULT_PROFILE_IMAGE_URL,
                contentDescription = "profile_image",
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            galleryLauncher.launch("image/*")
                        } else {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                    .size(64.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "닉네임",
                modifier = Modifier.padding(top = 16.dp),
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = uiState.nickname,
                onValueChange = {
                    onIntent(ProfileEditIntent.UpdateNickname(it))
                },
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()

            )
        }



    }
}