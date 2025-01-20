package com.jae464.presentation.contest

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.component.MainTabBackHandler
import com.jae464.presentation.contest.component.ContestItem
import com.jae464.presentation.contestdetail.ContestDetailIntent

@Composable
fun ContestHistoryRoute(
    padding: PaddingValues,
    viewModel: ContestHistoryViewModel = hiltViewModel(),
    onClickContest: (Long, String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainTabBackHandler()

    ContestHistoryScreen(
        padding = padding, uiState = uiState, onClickContest = onClickContest,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContestHistoryScreen(
    padding: PaddingValues,
    uiState: ContestHistoryUiState,
    onClickContest: (Long, String) -> Unit,
    onIntent: (ContestHistoryIntent) -> Unit = {}
) {
    val listState = rememberLazyListState()

    val isScrollingToEnd by remember(uiState.contests) {
        derivedStateOf {
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= uiState.contests.size - 2
        }
    }

    LaunchedEffect(isScrollingToEnd) {
        if (isScrollingToEnd && !uiState.isLoading && uiState.contests.size >= 20) {
            Log.d("HomeScreen", "isScrollingToEnd Fetching")
            onIntent(ContestHistoryIntent.LoadContestHistory)
        }
    }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        HeavenTopAppBar(
            paddingValues = padding,
            title = "지난 요리 대회",
            useNavigationIcon = false,
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 0.5.dp
        )
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = listState
        ) {
            items(uiState.contests.size) { index ->
                ContestItem(uiState.contests[index], onClickContest = onClickContest)
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp
                )
            }
        }
    }
}