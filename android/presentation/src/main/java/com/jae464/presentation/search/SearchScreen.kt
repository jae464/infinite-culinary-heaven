package com.jae464.presentation.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.jae464.presentation.component.RecipeItem
import com.jae464.presentation.search.component.SearchTopAppBar

@Composable
fun SearchRoute(
    contestId: Long?,
    onRecipeClick: (Long) -> Unit,
    onBackClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val keyword by viewModel.keyword.collectAsStateWithLifecycle()

    SearchScreen(
        contestId = contestId,
        uiState = uiState,
        keyword = keyword,
        onRecipeClick = onRecipeClick,
        onIntent = viewModel::handleIntent,
        onBackClick = onBackClick
    )

}

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    keyword: String,
    onRecipeClick: (Long) -> Unit,
    onIntent: (SearchIntent) -> Unit = {},
    contestId: Long?,
    onBackClick: () -> Unit
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
            onIntent(SearchIntent.FetchRecipePreviews)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        SearchTopAppBar(
            placeholderText = "레시피 제목으로 검색해보세요.",
            onBackClick = onBackClick,
            onSearchQueryChange = {
                onIntent(SearchIntent.UpdateKeyword(it))
            },
            searchQuery = keyword
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            items(uiState.recipePreviews.size) { index ->
                RecipeItem(uiState.recipePreviews[index], onClickRecipe = onRecipeClick)
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp
                )
            }
        }
    }
}
