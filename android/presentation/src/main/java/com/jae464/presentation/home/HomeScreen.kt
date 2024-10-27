package com.jae464.presentation.home

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.jae464.presentation.home.component.RecipeItem
import com.jae464.presentation.home.component.WeeklyIngredientSection

@Composable
fun HomeRoute(
    padding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickRecipe: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        padding = padding,
        uiState = uiState,
        onClickRecipe = onClickRecipe
    )

}

@Composable
fun HomeScreen(
    padding: PaddingValues,
    uiState: HomeUiState,
    onClickRecipe: (Long) -> Unit = {}
) {
    val context = LocalContext.current

    Box {
        LazyColumn(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Column {
                    WeeklyIngredientSection(
                        title = uiState.currentContest?.ingredient ?: "",
                        imageUrl = uiState.currentContest?.imageUrl ?: "",
                        description = uiState.currentContest?.description ?: ""
                    )
                    Text(
                        text = "10월 3주차 대회",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            items(uiState.recipePreviews.size) { index ->
                RecipeItem(uiState.recipePreviews[index], onClickRecipe = onClickRecipe)
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.White,
            onClick = { /*TODO*/ }
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