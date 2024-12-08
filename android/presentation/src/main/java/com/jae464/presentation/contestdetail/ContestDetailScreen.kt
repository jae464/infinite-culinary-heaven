package com.jae464.presentation.contestdetail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.jae464.presentation.component.RecipeItem
import com.jae464.presentation.home.HomeIntent

@Composable
fun ContestDetailRoute(
    contestId: Long,
    onBackClick: () -> Unit,
    onClickRecipe: (Long) -> Unit,
    viewModel: ContestDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ContestDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onClickRecipe = onClickRecipe,
        onIntent = viewModel::handleIntent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContestDetailScreen(
    uiState: ContestDetailUiState,
    onBackClick: () -> Unit,
    onClickRecipe: (Long) -> Unit = {},
    onIntent: (ContestDetailIntent) -> Unit = {}
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
            onIntent(ContestDetailIntent.LoadContestDetail)
        }
    }


    Box(
        modifier = Modifier.statusBarsPadding()
            .fillMaxSize()
    ) {
        Column {
            HeavenTopAppBar(
                title = "",
                useNavigationIcon = true,
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationClick = onBackClick,
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                state = listState
            ) {
                items(uiState.recipePreviews.size) { index ->
                    RecipeItem(uiState.recipePreviews[index], onClickRecipe = onClickRecipe)
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }
}