package com.jae464.presentation.register

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Step
import com.jae464.presentation.component.HeavenTopAppBar
import com.jae464.presentation.ui.theme.Gray20
import kotlinx.coroutines.launch

@Composable
fun RecipeRegisterRoute(
    recipeId: Long?,
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit,
    onShowSnackBar: suspend (String, String?) -> Unit,
    viewModel: RecipeRegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event = viewModel.event
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        event.collect {
            when (it) {
                RecipeRegisterEvent.RegisterSuccess -> {
                    Toast.makeText(context, "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    onRegisterSuccess()
                }
                RecipeRegisterEvent.RegisterFailure -> {
                    onShowSnackBar("등록에 실패했습니다.", null)
                }
            }
        }
    }

    RecipeRegisterScreen(
        uiState = uiState,
        onIntent = viewModel::handleIntent,
        onBackClick = onBackClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeRegisterScreen(
    uiState: RecipeRegisterUiState,
    onIntent: (RecipeRegisterIntent) -> Unit,
    onBackClick: () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(bottom = 84.dp)
                .verticalScroll(rememberScrollState())
        ) {

            HeavenTopAppBar(
                title = "레시피 등록",
                navigationIcon = Icons.Default.Close,
                onNavigationClick = onBackClick,
                actions = {

                }
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                thickness = 0.5.dp
            )

            RegisterForm(
                uiState = uiState,
                modifier = Modifier.fillMaxHeight(),
                onChangeThumbnailImage = { onIntent(RecipeRegisterIntent.SetThumbnailImage(it)) },
                onChangeTitle = { onIntent(RecipeRegisterIntent.SetTitle(it)) },
                onChangeDescription = { onIntent(RecipeRegisterIntent.SetDescription(it)) },
                onAddIngredient = { onIntent(RecipeRegisterIntent.AddIngredient(it)) },
                onRemoveIngredient = { onIntent(RecipeRegisterIntent.RemoveIngredient(it)) },
                onAddStep = { onIntent(RecipeRegisterIntent.AddStep(it)) },
                onRemoveStep = { onIntent(RecipeRegisterIntent.RemoveStep(it)) }
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = { onIntent(RecipeRegisterIntent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "등록 하기")
            }
        }
    }
}

@Composable
fun RegisterForm(
    modifier: Modifier = Modifier,
    uiState: RecipeRegisterUiState,
    onChangeThumbnailImage: (String?) -> Unit,
    onChangeTitle: (String) -> Unit,
    onChangeDescription: (String) -> Unit,
    onAddIngredient: (Ingredient) -> Unit,
    onRemoveIngredient: (Ingredient) -> Unit,
    onAddStep: (Step) -> Unit,
    onRemoveStep: (Step) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        RecipeThumbnailImage(
            thumbnailImage = uiState.thumbnailImage,
            onChangeThumbnailImage = onChangeThumbnailImage
        )
        Text(text = "제목", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.title,
            onValueChange = onChangeTitle,
            label = { Text("제목") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "소개", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.description,
            onValueChange = onChangeDescription,
            label = { Text("요리를 설명해주세요.") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "재료", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.ingredients.map {
                IngredientItem(
                    ingredient = it,
                    onClickDelete = { onRemoveIngredient(it) }
                )
            }

            IngredientForm(onAddIngredient = onAddIngredient)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "요리 순서", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.steps.map {
                StepItem(
                    step = it,
                    onClickDelete = { onRemoveStep(it) }
                )
            }
            StepForm(onAddStep = onAddStep)

        }

        Spacer(modifier = Modifier.height(16.dp))

    }

}

@Composable
fun RecipeThumbnailImage(
    modifier: Modifier = Modifier,
    thumbnailImage: String?,
    onChangeThumbnailImage: (String?) -> Unit
) {

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onChangeThumbnailImage(uri.toString())
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*") // 권한 허용 시 갤러리 열기
        } else {
            Toast.makeText(context, "권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(256.dp)
            .background(color = Gray20, shape = RoundedCornerShape(8.dp))
            .clickable {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    galleryLauncher.launch("image/*")
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
    ) {
        if (thumbnailImage != null) {
            Image(
                painter = rememberAsyncImagePainter(thumbnailImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "이미지 추가",
                modifier = Modifier.align(Alignment.Center)
            )
        }


    }
}

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = ingredient.name)
        Text(text = ingredient.quantity)
        Button(
            onClick = onClickDelete,
            modifier = Modifier.height(36.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "삭제")
        }
    }
}

@Composable
fun IngredientForm(
    onAddIngredient: (Ingredient) -> Unit
) {
    var currentIngredient by remember { mutableStateOf(Ingredient("", "")) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = currentIngredient.name,
            onValueChange = { currentIngredient = currentIngredient.copy(name = it) },
            label = { Text("재료 이름") },
            modifier = Modifier.weight(4f),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
        )
        OutlinedTextField(
            value = currentIngredient.quantity,
            onValueChange = { currentIngredient = currentIngredient.copy(quantity = it) },
            label = { Text("양") },
            modifier = Modifier.weight(4f),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
        )
        Button(
            onClick = {
                if (currentIngredient.name.isNotEmpty() && currentIngredient.quantity.isNotEmpty()) {
                    onAddIngredient(currentIngredient)
                    currentIngredient = Ingredient("", "")
                }
            },
            modifier = Modifier.height(36.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "추가")
        }
    }
}

@Composable
fun StepItem(
    step: Step,
    onClickDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = step.description,
            modifier = Modifier.weight(6f)
        )
        Box(
            modifier = Modifier
                .size(128.dp)
                .background(color = Gray20, shape = RoundedCornerShape(8.dp))
        ) {
            if (step.imageUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(step.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
        Button(
            onClick = onClickDelete,
            modifier = Modifier.height(36.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "삭제")

        }
    }
}

@Composable
fun StepForm(
    onAddStep: (Step) -> Unit
) {
    val context = LocalContext.current
    var currentStep by remember { mutableStateOf(Step(1, "", "")) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri
            currentStep = currentStep.copy(imageUrl = uri.toString())
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*") // 권한 허용 시 갤러리 열기
        } else {
            Toast.makeText(context, "권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = currentStep.description,
            onValueChange = { currentStep = currentStep.copy(description = it) },
            label = { Text("요리 과정에 대해 설명하세요.") },
            modifier = Modifier
                .weight(6f)
                .height(128.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray20)
        )
        Box(
            modifier = Modifier
                .size(128.dp)
                .background(color = Gray20, shape = RoundedCornerShape(8.dp))
                .clickable {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        galleryLauncher.launch("image/*")
                    } else {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
        ) {
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(currentStep.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "이미지 추가",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Button(
            onClick = {
                onAddStep(currentStep)
                currentStep = Step(1, "", "")
                selectedImageUri = null
            },
            modifier = Modifier.height(36.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "추가")
        }
    }
}
