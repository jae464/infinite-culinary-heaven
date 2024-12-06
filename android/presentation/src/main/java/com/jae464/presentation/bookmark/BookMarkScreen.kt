package com.jae464.presentation.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jae464.presentation.component.RecipeItem
import com.jae464.presentation.util.LaunchedEffectWithLifecycle
import kotlin.math.min

@Composable
fun BookMarkRoute(
    padding: PaddingValues,
    viewModel: BookMarkViewModel = hiltViewModel(),
    onClickRecipe: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookMarkScreen(
        padding = padding,
        uiState = uiState,
        onClickRecipe = onClickRecipe,
        onIntent = viewModel::handleIntent
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookMarkScreen(
    padding: PaddingValues,
    uiState: BookMarkUiState,
    onClickRecipe: (Long) -> Unit = {},
    onIntent: (BookMarkIntent) -> Unit = {}
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = {
            onIntent(BookMarkIntent.FetchBookMarkedRecipes)
        }
    )

    val offsetY = min(pullRefreshState.progress * 100, 80f)

    LaunchedEffectWithLifecycle {
        onIntent(BookMarkIntent.FetchBookMarkedRecipes)
    }

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .offset(y = offsetY.dp)
            .pullRefresh(pullRefreshState)
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
        ) {
            item {
                Text(
                    text = "스크랩",
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(uiState.bookMarkedRecipes.size) { index ->
                RecipeItem(
                    recipePreview = uiState.bookMarkedRecipes[index],
                    onClickRecipe = onClickRecipe
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp
                )
            }

        }
        if (pullRefreshState.progress > 0f) { // 화면을 당길 때만 Indicator 표시
            PullRefreshIndicator(
                refreshing = uiState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}