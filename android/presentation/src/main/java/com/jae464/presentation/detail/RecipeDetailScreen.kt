package com.jae464.presentation.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
import com.jae464.presentation.util.addFocusCleaner

@Composable
fun RecipeDetailRoute(
    recipeId: Long,
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToEditRecipe: (Long) -> Unit = {}
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
        onBackClick = onBackClick,
        onNavigateToEditRecipe = onNavigateToEditRecipe
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    uiState: RecipeDetailUiState,
    onIntent: (RecipeDetailIntent) -> Unit = {},
    onBackClick: () -> Unit,
    onNavigateToEditRecipe: (Long) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    var showImageDialog by remember { mutableStateOf(false) }
    var imageUrl by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val scrollState = rememberScrollState()

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
    ) {
        HeavenTopAppBar(
            title = uiState.recipe?.title ?: "",
            navigationIcon = Icons.Default.ArrowBack,
            useNavigationIcon = true,
            onNavigationClick = onBackClick,
            actions = {
                if (uiState.recipe?.isOwner == false) {
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
                if (uiState.recipe?.isOwner == true) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onNavigateToEditRecipe(uiState.recipe.id)
                        }
                    )
                    Spacer(modifier = Modifier.padding(end = 12.dp))
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onIntent(RecipeDetailIntent.DeleteRecipe(uiState.recipe.id))
                        }
                    )
                }

            }
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 0.5.dp
        )
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            if (uiState.recipe != null) {
                RecipeItem(recipe = uiState.recipe,
                    onClickImage = {
                        showImageDialog = true
                        imageUrl = it
                    },
                    onClickCommentIcon = {
                        showBottomSheet = true
                    }
                )
            }
        }

        // 댓글
        if (showBottomSheet && uiState.recipe != null) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = {
                    onIntent(RecipeDetailIntent.ClearCommentEditMode)
                    showBottomSheet = false
                },
                modifier = Modifier.wrapContentHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = minHeight, max = maxHeight)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.comments.size) {
                            CommentItem(
                                comment = uiState.comments[it],
                                isOwner = uiState.myInfo?.id == uiState.comments[it].userInfo.id,
                                onClickEdit = { commentId ->
                                    focusRequester.requestFocus()
                                    onIntent(RecipeDetailIntent.SetCommentEditMode(commentId))
                                    onIntent(RecipeDetailIntent.UpdateCommentInput(uiState.comments[it].content))
                                },
                                onClickDelete = { commentId ->
                                    onIntent(
                                        RecipeDetailIntent.DeleteComment(
                                            uiState.recipe.id,
                                            commentId
                                        )
                                    )
                                }
                            )
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
                                .focusRequester(focusRequester)

                        )
                        Icon(
                            imageVector = Icons.Default.Send,
                            tint = Green10,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                onIntent(
                                    RecipeDetailIntent.AddComment(
                                        uiState.recipe.id,
                                        uiState.commentInput
                                    )
                                )
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
    onClickCommentIcon: () -> Unit
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
                        Icon(
                            imageVector = Icons.Default.ChatBubbleOutline,
                            tint = Green10,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                onClickCommentIcon()
                            }
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


