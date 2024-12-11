package com.jae464.presentation.mypage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Dining
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jae464.domain.model.UserInfo
import com.jae464.presentation.component.MainTabBackHandler
import com.jae464.presentation.component.RoundedContentBox
import com.jae464.presentation.ui.theme.Gray20
import com.jae464.presentation.ui.theme.Green10
import com.jae464.presentation.util.ImageConstants

@Composable
fun MyPageRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    onClickEditProfile: (String, String?) -> Unit,
    isRefresh: Boolean
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainTabBackHandler()

    LaunchedEffect(Unit) {
        if (isRefresh) {
            viewModel.fetchUserInfo()
            Log.d("HomeScreen", "isRefresh Fetching")
        }
    }

    MyPageScreen(
        padding = padding,
        uiState = uiState,
        onClickEditProfile = onClickEditProfile
    )
}

@Composable
fun MyPageScreen(
    padding: PaddingValues,
    uiState: MyPageUiState,
    onClickEditProfile: (String, String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = Gray20),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        MyProfile(
            userInfo = uiState.userInfo,
            onClickEditProfile = onClickEditProfile
        )
        MyRecipe()
        MyContest()
    }
}

@Composable
fun MyProfile(
    userInfo: UserInfo?,
    onClickEditProfile: (String, String?) -> Unit
) {
    RoundedContentBox {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (userInfo != null) {
                AsyncImage(
                    model = userInfo.profileImageUrl ?: ImageConstants.DEFAULT_PROFILE_IMAGE_URL,
                    contentDescription = "user_image",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(64.dp),
                    contentScale = ContentScale.Fit
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = userInfo.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "#${userInfo.id}",
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = Color.DarkGray,
                        contentDescription = "edit_profile",
                        modifier = Modifier.clickable {
                            onClickEditProfile(
                                userInfo.name,
                                userInfo.profileImageUrl
                            )
                        }
                    )
                }
            }
            else {
                Text(text = "로그인이 필요한 서비스입니다.")
            }
        }
    }
}

@Composable
fun MyRecipe() {
    RoundedContentBox {
        Column {
            Text(text = "레시피")
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 0.5.dp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                MenuItem(
                    imageVector = Icons.Outlined.Dining,
                    title = "나의 레시피"
                )
                MenuItem(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    title = "스크랩"
                )
            }
        }
    }
}

@Composable
fun MyContest() {
    RoundedContentBox {
        Column {
            Text(text = "대회")
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 0.5.dp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                MenuItem(
                    imageVector = Icons.Default.FoodBank,
                    title = "참여한 대회"
                )
            }
        }
    }
}

@Composable
fun MenuItem(
    imageVector: ImageVector,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = imageVector,
            contentDescription = title,
            tint = Color.Gray
        )
        Text(
            text = title,
            fontSize = 18.sp
        )
    }
}

