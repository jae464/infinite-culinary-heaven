package com.jae464.presentation.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.Step
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.detail.component.RecipeDetailContentBox
import com.jae464.presentation.ui.theme.Gray20
import com.jae464.presentation.ui.theme.Green10
import com.jae464.presentation.ui.theme.Red10

@Composable
fun RecipeDetailRoute(
    recipeId: Long,
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event = viewModel.event
    val context = LocalContext.current

    LaunchedEffect(recipeId) {
        viewModel.handleIntent(RecipeDetailIntent.FetchRecipe(recipeId))
    }

    LaunchedEffect(Unit) {
        event.collect {
            when (it) {
                RecipeDetailEvent.DeleteSuccess -> {
                    Toast.makeText(context, "삭제에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    onNavigateToHome()
                }

                RecipeDetailEvent.AddBookMarkSuccess -> {
                    Toast.makeText(context, "북마크에 추가했습니다.", Toast.LENGTH_SHORT).show()
                }

                RecipeDetailEvent.DeleteBookMarkSuccess -> {
                    Toast.makeText(context, "북마크에서 제거했습니다.", Toast.LENGTH_SHORT).show()
                }

                RecipeDetailEvent.LikeSuccess -> {
                    Toast.makeText(context, "좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show()
                }

                RecipeDetailEvent.UnlikeSuccess -> {
                    Toast.makeText(context, "좋아요를 해제했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    RecipeDetailScreen(
        uiState = uiState,
        onIntent = viewModel::handleIntent,
        onBackClick = onBackClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    uiState: RecipeDetailUiState,
    onIntent: (RecipeDetailIntent) -> Unit = {},
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Gray20
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        HeavenTopAppBar(
            title = uiState.recipe?.title ?: "",
            navigationIcon = Icons.Default.ArrowBack,
            onNavigationClick = onBackClick,
            actions = {
                // todo 좋아요, 스크랩은 현재 테스트를 위해 다 보이게 했지만, 추후 본인이 아닐때만 표시되도록 수정
                if (uiState.recipe != null) {
                    IconButton(onClick = {
                        if (uiState.recipe.isLiked) {
                            onIntent(RecipeDetailIntent.UnlikeRecipe(uiState.recipe.id))
                        } else {
                            onIntent(RecipeDetailIntent.LikeRecipe(uiState.recipe.id))
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            tint = if (uiState.recipe.isLiked) Red10 else Color.LightGray,
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = {
                        if (uiState.recipe.isBookMarked) {
                            onIntent(RecipeDetailIntent.DeleteBookMark(uiState.recipe.id))
                        } else {
                            onIntent(RecipeDetailIntent.AddBookMark(uiState.recipe.id))
                        }


                    }) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            tint = if (uiState.recipe.isBookMarked) Green10 else Color.LightGray,
                            contentDescription = null
                        )
                    }
                }


                if (uiState.recipe?.isOwner == true) {
                    IconButton(onClick = {
                        if (uiState.recipe != null) {
                            onIntent(RecipeDetailIntent.DeleteRecipe(uiState.recipe.id))
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 0.5.dp
        )
        if (uiState.recipe != null) {
            RecipeItem(recipe = uiState.recipe)
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe
) {
    Column {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(8.dp))
                        .height(240.dp),
                    contentScale = ContentScale.Fit
                )
            }

            RecipeDetailContentBox {
                Column {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = recipe.description)
                }
            }

            RecipeDetailContentBox {
                Column {
                    Text(
                        text = "재료",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Color.Black,
                        thickness = 1.5.dp
                    )
                    Column(

                    ) {
                        recipe.ingredients.forEach { ingredient ->
                            IngredientItem(ingredient)
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                thickness = 0.5.dp
                            )
                        }
                    }
                }
            }

            RecipeDetailContentBox {
                Column {
                    Row {
                        Text(
                            text = "조리순서",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                    recipe.steps.forEachIndexed { index, step ->
                        StepItem(step = step, index = index + 1)
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = ingredient.name, fontSize = 16.sp)
        Text(text = ingredient.quantity, fontSize = 16.sp)
    }
}

@Composable
fun StepItem(step: Step, index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "${index}.", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = step.description, fontSize = 16.sp, lineHeight = 20.sp)
        }

        if (step.imageUrl != null) {
            AsyncImage(
                model = step.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}


