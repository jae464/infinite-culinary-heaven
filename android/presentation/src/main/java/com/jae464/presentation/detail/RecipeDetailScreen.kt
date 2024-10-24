package com.jae464.presentation.detail

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jae464.domain.model.Recipe
import com.jae464.presentation.component.HeavenTopAppBar

@Composable
fun RecipeDetailRoute(
    recipeId: Long,
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RecipeDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    uiState: RecipeDetailUiState,
    onBackClick: () -> Unit
) {
    val recipe = uiState.recipe

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        HeavenTopAppBar(
            title = recipe?.title ?: "",
            navigationIcon = Icons.Default.ArrowBack,
            onNavigationClick = onBackClick,
        )
        if (recipe != null) {
            RecipeItem(recipe = recipe)
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe
) {
    Column {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            // Title
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Author
            Text(text = "By ${recipe.author}")

            Spacer(modifier = Modifier.height(8.dp))

            // Score and Serving size
            Row {
                Text(text = "Score: ${recipe.score}")
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Serving: ${recipe.servingSize}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = recipe.description)

            Spacer(modifier = Modifier.height(16.dp))

            // Divider
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ingredients
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            recipe.ingredients.forEach { ingredient ->
                Text(text = "- $ingredient")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Steps
            Text(
                text = "Steps",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            recipe.steps.forEachIndexed { index, step ->
                Text(text = "${index + 1}. $step")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Cook Time
            Text(text = "Cook time: ${recipe.cookTime}")

            Spacer(modifier = Modifier.height(16.dp))

            // Video Button
            recipe.videoUrl?.let {
                TextButton(onClick = { /* Open video */ }) {
                    Text(text = "Watch Video")
                }
            }
        }
    }
}


