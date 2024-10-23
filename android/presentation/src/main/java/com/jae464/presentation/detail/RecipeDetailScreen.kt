package com.jae464.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RecipeDetailRoute(
    recipeId: Long
) {
    val viewModel: RecipeDetailViewModel = hiltViewModel()
    RecipeDetailScreen()

}

@Composable
fun RecipeDetailScreen() {
    Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        Text(text = "레시피 상세 화면")
    }
}