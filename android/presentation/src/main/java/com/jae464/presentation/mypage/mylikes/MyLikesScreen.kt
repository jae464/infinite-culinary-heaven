package com.jae464.presentation.mypage.mylikes

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.component.RecipeItem

@Composable
fun MyLikesRoute(
    onBackClick: () -> Unit,
    onClickRecipe: (Long) -> Unit,
    viewModel: MyLikesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyLikesScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onClickRecipe = onClickRecipe,
        onIntent = viewModel::handleIntent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLikesScreen(
    uiState: MyLikesUiState,
    onClickRecipe: (Long) -> Unit = {},
    onBackClick: () -> Unit,
    onIntent: (MyLikesIntent) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val isScrollingToEnd by remember(uiState.recipes) {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= uiState.recipes.size - 2
        }
    }
    LaunchedEffect(isScrollingToEnd) {
        if (isScrollingToEnd && !uiState.isLoading && uiState.recipes.size >= 20) {
            Log.d("HomeScreen", "isScrollingToEnd Fetching")
            onIntent(MyLikesIntent.FetchMyLikesRecipePreviews)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HeavenTopAppBar(
            title = "좋아요 한 레시피",
            navigationIcon = Icons.Default.ArrowBack,
            useNavigationIcon = true,
            onNavigationClick = onBackClick,
        )
        if (uiState.recipes.isEmpty() && !uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "아직 좋아요 한 레시피가 없습니다.",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black,
                    fontSize = 18.sp,
                )
            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 0.5.dp
        )
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            state = listState
        ) {
            items(uiState.recipes.size) { index ->
                RecipeItem(uiState.recipes[index], onClickRecipe = onClickRecipe)
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp
                )
            }
        }
    }
}