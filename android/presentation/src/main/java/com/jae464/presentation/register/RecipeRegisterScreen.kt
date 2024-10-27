package com.jae464.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.ui.theme.Gray20

@Composable
fun RecipeRegisterRoute(
    recipeId: Long?,
    onBackClick: () -> Unit
) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RecipeRegisterScreen(
//        uiState = uiState,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeRegisterScreen(
//    uiState: RecipeRegisterUiState,
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(bottom = 84.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeavenTopAppBar(
                title = "레시피 등록",
                navigationIcon = Icons.Default.Close,
                onNavigationClick = onBackClick,
                actions = {

                }
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                thickness = 0.5.dp
            )
            RegisterForm(modifier = Modifier.fillMaxHeight())
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = { /* Handle submission */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "등록 하기")
            }
        }
    }
}

@Composable
fun RegisterForm(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "제목", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("제목") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "소개", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("요리를 설명해주세요.") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "재료", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // todo 여기에 Column으로 추가된 재료가 들어간다.

                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("재료 이름") },
                    modifier = Modifier.weight(4f),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("양") },
                    modifier = Modifier.weight(4f),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
                )
                Button(
                    onClick = { /* Handle submission */ },
                    modifier = Modifier.height(36.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "추가")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "요리 순서", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // todo 여기에 Column으로 추가된 요리순서가 들어간다.

                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("요리 과정에 대해 설명하세요.") },
                    modifier = Modifier
                        .weight(6f)
                        .height(128.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
                )
                Box(
                    modifier = Modifier
                        .size(128.dp)
                        .background(color = Gray20, shape = RoundedCornerShape(8.dp))
                ) {
                    Image(
                        imageVector = Icons.Default.AddAPhoto,
                        modifier = Modifier.align(Alignment.Center),
                        contentDescription = null,
//                        modifier = Modifier.size(128.dp).background(color = Gray20, shape = RoundedCornerShape(8.dp))
                    )
                }
                Button(
                    onClick = { /* Handle submission */ },
                    modifier = Modifier.height(36.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "추가")

                }
            }

        }



        Spacer(modifier = Modifier.height(16.dp))


    }

}