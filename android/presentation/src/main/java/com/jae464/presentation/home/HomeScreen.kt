package com.jae464.presentation.home

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jae464.domain.model.RecipePreview
import com.jae464.presentation.component.RecipeItem
import com.jae464.presentation.home.component.WeeklyIngredientSection
import kotlin.math.min

@Composable
fun HomeRoute(
    padding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickRecipe: (Long) -> Unit,
    onClickRegister: () -> Unit,
    isRefresh: Boolean = false
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (isRefresh) {
            viewModel.refreshRecipePreviews()
        }
    }

    HomeScreen(
        padding = padding,
        uiState = uiState,
        onClickRecipe = onClickRecipe,
        onClickRegister = onClickRegister,
        onIntent = viewModel::handleIntent
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    padding: PaddingValues,
    uiState: HomeUiState,
    onClickRecipe: (Long) -> Unit = {},
    onClickRegister: () -> Unit = {},
    onIntent: (HomeIntent) -> Unit = {}
) {

    Log.d("HomeScreen", "Home Screen is Rendered.")

    val listState = rememberLazyListState()

    val isScrollingToEnd by remember(uiState.recipePreviews) {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= uiState.recipePreviews.size - 2
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = {
            onIntent(HomeIntent.RefreshRecipePreviews)
        }
    )

    val offsetY = min(pullRefreshState.progress * 100, 80f)

    LaunchedEffect(isScrollingToEnd) {
        if (isScrollingToEnd && !uiState.isLoading) {
            onIntent(HomeIntent.FetchRecipePreviews)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .offset(y = offsetY.dp)
            .padding(padding)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            item {
                Column {
                    WeeklyIngredientSection(
                        title = uiState.currentContest?.ingredient ?: "",
                        imageUrl = uiState.currentContest?.imageUrl ?: "",
                        description = uiState.currentContest?.description ?: ""
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = uiState.currentContest?.description ?: "",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            items(uiState.recipePreviews.size) { index ->
                RecipeItem(uiState.recipePreviews[index], onClickRecipe = onClickRecipe)
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            onClick = onClickRegister
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
        PullRefreshIndicator(
            refreshing = uiState.isLoading,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

fun Int.getResourceUri(context: Context): String {
    return context.resources.let {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(it.getResourcePackageName(this))		// it : resources, this : ResId(Int)
            .appendPath(it.getResourceTypeName(this))		// it : resources, this : ResId(Int)
            .appendPath(it.getResourceEntryName(this))		// it : resources, this : ResId(Int)
            .build()
            .toString()
    }
}