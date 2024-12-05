package com.jae464.presentation.contestdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.component.RecipeItem

@Composable
fun ContestDetailRoute(
    contestId: Long,
    onBackClick: () -> Unit,
    onClickRecipe: (Long) -> Unit,
    viewModel: ContestDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ContestDetailIntent.LoadContestDetail(contestId))
    }

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
    Box(
        modifier = Modifier.statusBarsPadding()
            .fillMaxSize()
    ) {
        Column {
            HeavenTopAppBar(
                title = "",
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationClick = onBackClick,
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
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