package com.jae464.presentation.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jae464.presentation.component.RecipeItem

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
        onClickRecipe = onClickRecipe
    )
}

@Composable
fun BookMarkScreen(
    padding: PaddingValues,
    uiState: BookMarkUiState,
    onClickRecipe: (Long) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(uiState.bookMarkedRecipes.size) { index ->
                RecipeItem(
                    recipePreview = uiState.bookMarkedRecipes[index],
                    onClickRecipe = onClickRecipe
                )
            }
        }
    }
}