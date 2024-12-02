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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jae464.presentation.component.RecipeItem
import com.jae464.presentation.home.component.WeeklyIngredientSection

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
            viewModel.fetchRecipePreviews()
        }
    }

    HomeScreen(
        padding = padding,
        uiState = uiState,
        onClickRecipe = onClickRecipe,
        onClickRegister = onClickRegister
    )

}

@Composable
fun HomeScreen(
    padding: PaddingValues,
    uiState: HomeUiState,
    onClickRecipe: (Long) -> Unit = {},
    onClickRegister: () -> Unit = {}
) {

    Log.d("HomeScreen", "Home Screen is Rendered.")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
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