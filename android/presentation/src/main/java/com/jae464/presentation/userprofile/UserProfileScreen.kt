package com.jae464.presentation.userprofile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jae464.domain.model.UserInfo
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.component.RecipeItem
import com.jae464.presentation.component.RoundedContentBox
import com.jae464.presentation.contestdetail.ContestDetailIntent
import com.jae464.presentation.ui.theme.Gray20
import com.jae464.presentation.ui.theme.Green10
import com.jae464.presentation.util.ImageConstants

@Composable
fun UserProfileRoute(
    userId: Long,
    onBackClick: () -> Unit,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event = viewModel.event
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        event.collect {
            when (it) {
                UserProfileEvent.FetchUserInfoFailed -> {
                    Toast.makeText(context, "유저 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    UserProfileScreen(
        uiState = uiState,
        onIntent = viewModel::handleIntent,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    uiState: UserProfileUiState,
    onIntent: (UserProfileIntent) -> Unit,
    onBackClick: () -> Unit
) {
    val listState = rememberLazyListState()

    val isScrollingToEnd by remember(uiState.recipePreviews) {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= uiState.recipePreviews.size - 2
        }
    }

    LaunchedEffect(isScrollingToEnd) {
        if (isScrollingToEnd && !uiState.isLoading && uiState.recipePreviews.size >= 20) {
            Log.d("HomeScreen", "isScrollingToEnd Fetching")
            onIntent(UserProfileIntent.FetchRecipePreviews)
        }
    }
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
        Spacer(modifier = Modifier.height(16.dp))
        RoundedContentBox {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.userInfo != null) {
                    UserProfile(
                        userInfo = uiState.userInfo,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    UserFollowInfo(
                        followerCount = uiState.userInfo.followerCount ?: 0,
                        followingCount = uiState.userInfo.followingCount ?: 0,
                        onClickFollowInfo = {},
                        onClickFollowingInfo = {}
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (uiState.isMe) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = "프로필 수정", fontSize = 16.sp)
                        }
                    }
                    else {
                        Button(
                            onClick = {
                                if (uiState.isFollowing) {
                                    onIntent(UserProfileIntent.UnfollowUser)
                                }
                                else {
                                    onIntent(UserProfileIntent.FollowUser)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (uiState.isFollowing) Color.LightGray else Green10,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = if (uiState.isFollowing) "팔로우 해제" else "팔로우", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        RoundedContentBox {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "작성한 레시피",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (uiState.recipePreviews.isEmpty() && !uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.LightGray)
                    ) {
                        Text(
                            text = "작성한 레시피가 없습니다.",
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                ) {
                    items(uiState.recipePreviews.size) { index ->
                        RecipeItem(uiState.recipePreviews[index], onClickRecipe = {})
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            thickness = 0.5.dp
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun UserProfile(modifier: Modifier = Modifier, userInfo: UserInfo) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = userInfo.profileImageUrl ?: ImageConstants.DEFAULT_PROFILE_IMAGE_URL,
            contentDescription = "user_image",
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = userInfo.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "#${userInfo.id}",
            color = Color.DarkGray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun UserFollowInfo(
    modifier: Modifier = Modifier,
    followerCount: Int,
    followingCount: Int,
    onClickFollowInfo: () -> Unit,
    onClickFollowingInfo: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 팔로워 정보
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { onClickFollowInfo() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "팔로워",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Text(
                text = followerCount.toString(),
//                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }

        // 팔로잉 정보
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { onClickFollowingInfo() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "팔로잉",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Text(
                text = followingCount.toString(),
//                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}