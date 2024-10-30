package com.jae464.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jae464.presentation.history.component.ContestItem

@Composable
fun ContestHistoryRoute(
    padding: PaddingValues,
    viewModel: ContestHistoryViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ContestHistoryScreen(padding = padding, uiState = uiState)
}

@Composable
fun ContestHistoryScreen(
    padding: PaddingValues,
    uiState: ContestHistoryUiState
) {
    LazyColumn(
        modifier = Modifier.padding(padding).padding(horizontal = 16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text(
                text = "지난 요리 대회",
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        items(uiState.contests.size) { index ->
            ContestItem(uiState.contests[index])
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 0.5.dp
            )
        }
    }
}