package com.jae464.presentation.search

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
import com.jae464.presentation.component.RecipeItem
import com.jae464.presentation.home.component.WeeklyIngredientSection
import com.jae464.presentation.search.component.SearchTopAppBar

@Composable
fun SearchRoute(
    contestId: Long?,
    onBackClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val keyword by viewModel.keyword.collectAsStateWithLifecycle()

    SearchScreen(
        contestId = contestId,
        uiState = uiState,
        keyword = keyword,
        onIntent = viewModel::handleIntent,
        onBackClick = onBackClick
    )

}

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    keyword: String,
    onIntent: (SearchIntent) -> Unit = {},
    contestId: Long?,
    onBackClick: () -> Unit
) {

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        SearchTopAppBar(
            placeholderText = "레시피 제목으로 검색해보세요,",
            onBackClick = onBackClick,
            onSearchQueryChange = {
                onIntent(SearchIntent.UpdateKeyword(it))
            },
            searchQuery = keyword
        )
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            items(uiState.recipes.size) { index ->
                RecipeItem(uiState.recipes[index], onClickRecipe = {})
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp
                )
            }
        }
    }
}
