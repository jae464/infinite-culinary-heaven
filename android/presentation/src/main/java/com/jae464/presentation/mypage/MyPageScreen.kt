package com.jae464.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jae464.domain.model.UserInfo
import com.jae464.presentation.component.RoundedContentBox
import com.jae464.presentation.ui.theme.Gray20
import com.jae464.presentation.ui.theme.Green10
import com.jae464.presentation.ui.theme.Green2
import com.jae464.presentation.ui.theme.Green5
import com.jae464.presentation.ui.theme.PastelGreen

@Composable
fun MyPageRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreen(
        padding = padding,
        uiState = uiState
    )
}

@Composable
fun MyPageScreen(
    padding: PaddingValues,
    uiState: MyPageUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = Gray20),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MyProfile(
            userInfo = uiState.userInfo
        )
        MyRecipe()
        MyContest()
    }
}

@Composable
fun MyProfile(
    userInfo: UserInfo?
) {
    RoundedContentBox {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (userInfo != null) {
                AsyncImage(
                    model = userInfo.profileImageUrl,
                    contentDescription = "user_image",
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(64.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = userInfo.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
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
            // todo 일단 여백처리
            Spacer(modifier = Modifier.height(60.dp))
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
            // todo 일단 여백처리
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

