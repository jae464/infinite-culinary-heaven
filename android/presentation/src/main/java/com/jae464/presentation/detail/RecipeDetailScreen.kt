package com.jae464.presentation.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.jae464.presentation.component.ImageDetailDialog
import com.jae464.presentation.detail.component.CommentItem
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
        viewModel.handleIntent(RecipeDetailIntent.FetchComments(recipeId))
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
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    var showImageDialog by remember { mutableStateOf(false) }
    var imageUrl by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val minHeight = screenHeight * 0.5f
    val maxHeight = screenHeight * 0.8f


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
            useNavigationIcon = true,
            onNavigationClick = onBackClick,
            actions = {
                // todo 좋아요, 스크랩은 현재 테스트를 위해 다 보이게 했지만, 추후 본인이 아닐때만 표시되도록 수정
                if (uiState.recipe != null) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = if (uiState.recipe.isLiked) Red10 else Color.LightGray,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            if (uiState.recipe.isLiked) {
                                onIntent(RecipeDetailIntent.UnlikeRecipe(uiState.recipe.id))
                            } else {
                                onIntent(RecipeDetailIntent.LikeRecipe(uiState.recipe.id))
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(end = 12.dp))

                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        tint = if (uiState.recipe.isBookMarked) Green10 else Color.LightGray,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            if (uiState.recipe.isBookMarked) {
                                onIntent(RecipeDetailIntent.DeleteBookMark(uiState.recipe.id))
                            } else {
                                onIntent(RecipeDetailIntent.AddBookMark(uiState.recipe.id))
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(end = 12.dp))
                }
                Icon(
                    imageVector = Icons.Default.ChatBubbleOutline,
                    tint = Green10,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        showBottomSheet = true
                    }
                )
                Spacer(modifier = Modifier.padding(end = 12.dp))

                if (uiState.recipe?.isOwner == true) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onIntent(RecipeDetailIntent.DeleteRecipe(uiState.recipe.id))
                        }
                    )
                    Spacer(modifier = Modifier.padding(end = 12.dp))
                }

            }
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 0.5.dp
        )
        if (uiState.recipe != null) {
            RecipeItem(recipe = uiState.recipe, onClickImage = {
                showImageDialog = true
                imageUrl = it
            })
        }

        // 댓글
        if (showBottomSheet) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = {
                    showBottomSheet = false
                },
                modifier = Modifier.wrapContentHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = minHeight, max = maxHeight)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.comments.size) {
                            CommentItem(uiState.comments[it])
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = uiState.commentInput,
                            onValueChange = {
                                onIntent(RecipeDetailIntent.UpdateCommentInput(it))
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                focusedContainerColor = Color(0xFFF5F5F5),
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(0.8f)
                                .padding(horizontal = 16.dp, vertical = 8.dp)

                        )
                        Icon(
                            imageVector = Icons.Default.Send,
                            tint = Green10,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                if (uiState.recipe != null) {
                                    onIntent(RecipeDetailIntent.AddComment(uiState.recipe.id, uiState.commentInput))
                                }
                            }
                        )
                    }
                }

            }
        }

    }
    if (showImageDialog && imageUrl.isNotBlank()) {
        Log.d("RecipeDetailScreen", "Image Detail Dialog imageUrl: $imageUrl")
        ImageDetailDialog(
            imageUrl = imageUrl,
            onDismiss = { showImageDialog = false }
        )
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    onClickImage: (String) -> Unit,
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
                        .clickable {
                            onClickImage(recipe.imageUrl)
                        }
                        .height(240.dp),
                    contentScale = ContentScale.Fit
                )
            }

            RecipeDetailContentBox {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = recipe.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = recipe.description)
                }
            }

            RecipeDetailContentBox {
                Column {
                    Text(
                        text = "재료",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
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
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                    recipe.steps.forEachIndexed { index, step ->
                        StepItem(step = step, index = index + 1, onClickImage = onClickImage)
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
fun StepItem(step: Step, index: Int, onClickImage: (String) -> Unit) {
    val imageUrl = step.imageUrl
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
            Text(text = "${index}.", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Text(text = step.description, fontSize = 16.sp, lineHeight = 20.sp)
        }

        if (imageUrl != null) {
            AsyncImage(
                model = step.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onClickImage(imageUrl) },
                contentScale = ContentScale.Crop
            )
        }
    }
}


