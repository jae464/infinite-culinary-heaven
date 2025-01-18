package com.jae464.presentation.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.component.MainTabBackHandler
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

    MainTabBackHandler()

    BookMarkScreen(
        padding = padding,
        uiState = uiState,
        onClickRecipe = onClickRecipe,
        onIntent = viewModel::handleIntent
    )

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        HeavenTopAppBar(
            paddingValues = padding,
            title = "스크랩",
            useNavigationIcon = false,
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 0.5.dp
        )
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
                .offset(y = offsetY.dp)
                .pullRefresh(pullRefreshState)
        ) {
            if (pullRefreshState.progress > 0f) { // 화면을 당길 때만 Indicator 표시
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        PullRefreshIndicator(
                            refreshing = uiState.isLoading,
                            state = pullRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                }
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
        if (uiState.bookMarkedRecipes.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "스크랩한 레시피가 없습니다.",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black,
                    fontSize = 18.sp,
                )
            }
        }
    }
}