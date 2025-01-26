package com.jae464.presentation.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.component.RoundedContentBox
import com.jae464.presentation.ui.theme.Gray20

@Composable
fun SettingRoute(
    onBackClick: () -> Unit,
) {
    SettingScreen(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Gray20),
    ) {
        HeavenTopAppBar(
            title = "설정",
            navigationIcon = Icons.Default.ArrowBack,
            useNavigationIcon = true,
            onNavigationClick = onBackClick,
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 0.5.dp
        )
        Spacer(modifier = Modifier.height(16.dp))
        NotificationSetting()
        Spacer(modifier = Modifier.height(16.dp))
        EtcSetting()
    }
}

@Composable
fun NotificationSetting() {
    var isNotificationEnabled by remember { mutableStateOf(true) } // todo 알림 상태 DataStore에 저장한거 가져오도록 변경하기

    RoundedContentBox {
        Column {
            Text(text = "알림")
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "알림 설정", fontSize = 18.sp)
                    Switch(
                        checked = isNotificationEnabled,
                        onCheckedChange = { isNotificationEnabled = it }
                    )
                }
            }
        }
    }
}

@Composable
fun EtcSetting() {
    RoundedContentBox {
        Column {
            Text(text = "기타")
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
                Text(text = "로그아웃", fontSize = 18.sp)
                Text(text= "회원탈퇴", fontSize = 18.sp)
            }
        }
    }
}