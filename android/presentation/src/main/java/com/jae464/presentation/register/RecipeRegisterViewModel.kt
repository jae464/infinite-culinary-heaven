package com.jae464.presentation.register

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Step
import com.jae464.domain.model.StepCreate
import com.jae464.domain.model.StepUpdate
import com.jae464.domain.repository.ContestRepository
import com.jae464.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class RecipeRegisterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val recipeRepository: RecipeRepository,
    private val contestRepository: ContestRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeRegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<RecipeRegisterEvent>()
    val event = _event.asSharedFlow()

    init {
        savedStateHandle.get<Long>("recipeId")?.let { recipeId ->
            fetchExistingRecipe(recipeId)
        }
    }

    fun handleIntent(intent: RecipeRegisterIntent) {
        when (intent) {
            is RecipeRegisterIntent.SetThumbnailImage -> setThumbnailImage(intent.thumbnailImage)
            is RecipeRegisterIntent.SetTitle -> setTitle(intent.title)
            is RecipeRegisterIntent.SetDescription -> setDescription(intent.description)
            is RecipeRegisterIntent.AddIngredient -> addIngredient(intent.ingredient)
            is RecipeRegisterIntent.AddStep -> addStep(intent.step)
            is RecipeRegisterIntent.RemoveIngredient -> removeIngredient(intent.ingredient)
            is RecipeRegisterIntent.RemoveStep -> removeStep(intent.step)
            is RecipeRegisterIntent.Submit -> submit()
        }
    }

    private fun fetchExistingRecipe(recipeId: Long) {
        viewModelScope.launch {
            recipeRepository.getRecipeById(recipeId)
                .onSuccess { recipe ->
                    Log.d("RecipeRegisterViewModel", "fetchExistingRecipe: $recipe")
                    _uiState.update { state ->
                        state.copy(
                            thumbnailImage = recipe.imageUrl,
                            title = recipe.title,
                            description = recipe.description,
                            ingredients = recipe.ingredients,
                            steps = recipe.steps
                        )
                    }
                }
                .onFailure {
                    Log.d("RecipeRegisterViewModel", "${it.message}")
                }
        }
    }

    private fun setThumbnailImage(thumbnailImage: String?) {
        _uiState.update { it.copy(thumbnailImage = thumbnailImage) }
    }

    private fun setTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    private fun setDescription(description: String) {
        _uiState.update { it.copy(description = description) }

    }

    private fun setIngredients(ingredients: List<Ingredient>) {
        _uiState.update { it.copy(ingredients = ingredients) }
    }

    private fun setSteps(steps: List<Step>) {
        _uiState.update { it.copy(steps = steps) }
    }

    private fun addIngredient(ingredient: Ingredient) {
        setIngredients(_uiState.value.ingredients + ingredient)
    }

    private fun removeIngredient(ingredient: Ingredient) {
        setIngredients(_uiState.value.ingredients - ingredient)
    }


    private fun removeStep(step: Step) {
        setSteps(_uiState.value.steps - step)
    }

    private fun addStep(step: Step) {
        setSteps(_uiState.value.steps + step)
    }

    // todo need refactoring
    private fun submit() {

        // handle edit
        if (savedStateHandle.get<Long>("recipeId") != null) {
            updateRecipe()
            return
        }

        viewModelScope.launch {
            if (!validateForm()) return@launch

            val thumbnailImageUri = _uiState.value.thumbnailImage?.toUri()
            var thumbnailImageFile: File? = null

            if (thumbnailImageUri != null) {
                thumbnailImageFile = convertToFile(thumbnailImageUri)
            }

            if (thumbnailImageFile == null) return@launch

            val stepFiles = _uiState.value.steps.map {
                val stepUri = it.imageUrl?.toUri()
                if (stepUri != null) {
                    convertToFile(stepUri)
                } else {
                    null
                }
            }

            val imageFiles =
                (listOfNotNull(thumbnailImageFile) + stepFiles).filterNotNull().distinct()

            // todo Consider NavArgument
            val currentContest = contestRepository.getCurrentContest().getOrNull() ?: return@launch

            _uiState.update { state -> state.copy(isRegistering = true) }

            recipeRepository.registerRecipe(
                images = imageFiles,
                thumbnailImageName = thumbnailImageFile.name,
                title = _uiState.value.title,
                description = _uiState.value.description,
                ingredients = _uiState.value.ingredients,
                steps = _uiState.value.steps.mapIndexed { index, step ->
                    StepCreate(
                        step = index + 1,
                        description = step.description,
                        imageName = if (step.imageUrl != null) getFileName(
                            step.imageUrl!!.toUri()
                        ) else null
                    )
                },
                contestId = currentContest.id
            ).onSuccess {
                _uiState.update { state -> state.copy(isRegistering = false) }
                _event.emit(RecipeRegisterEvent.RegisterSuccess)
            }.onFailure {
                Log.d("RecipeRegisterViewModel", "registerRecipe: $it")
                _uiState.update { state -> state.copy(isRegistering = false) }
                _event.emit(RecipeRegisterEvent.RegisterFailure)
            }

        }

    }

    // todo need refactoring
    private fun updateRecipe() {
        val recipeId = savedStateHandle.get<Long>("recipeId") ?: return

        viewModelScope.launch {
            if (!validateForm()) return@launch
            val thumbnailImage = uiState.value.thumbnailImage ?: return@launch
            val isThumbnailChanged = thumbnailImage.startsWith("content://")
            val imageFiles = mutableListOf<File>()
            var thumbnailImageFile: File?

            // when thumbnail changed. if not changed -> starts with https://
            if (isThumbnailChanged) {
                thumbnailImageFile = convertToFile(thumbnailImage.toUri()) ?: return@launch
                imageFiles.add(thumbnailImageFile)
            }

            val steps = uiState.value.steps
            steps.forEach { step ->
                val stepImageUrl = step.imageUrl ?: return@forEach
                if (stepImageUrl.startsWith("content://")) {
                    val stepImageFile = convertToFile(stepImageUrl.toUri()) ?: return@launch
                    imageFiles.add(stepImageFile)
                }
            }

            _uiState.update { state -> state.copy(isRegistering = true) }

            recipeRepository.updateRecipe(
                recipeId = recipeId,
                images = imageFiles,
                title = uiState.value.title,
                description = uiState.value.description,
                thumbnailImage = if (isThumbnailChanged) getFileName(thumbnailImage.toUri()) ?: thumbnailImage else thumbnailImage,
                ingredients = uiState.value.ingredients,
                steps = steps.mapIndexed { index, step ->
                    val isImageChanged = step.imageUrl?.startsWith("content://") ?: false
                    val stepImageUrl = step.imageUrl
                    StepUpdate(
                        step = index + 1,
                        description = step.description,
                        imageName = if (isImageChanged && stepImageUrl != null) getFileName(stepImageUrl.toUri()) else null,
                        imageUrl = if (!isImageChanged) stepImageUrl else null
                    )
                }
            ).onSuccess {
                _uiState.update { state -> state.copy(isRegistering = false) }
                _event.emit(RecipeRegisterEvent.RegisterSuccess)
            }.onFailure {
                _uiState.update { state -> state.copy(isRegistering = false) }
                _event.emit(RecipeRegisterEvent.RegisterFailure)
            }

        }
    }

    private suspend fun validateForm(): Boolean {
        val title = _uiState.value.title
        val description = _uiState.value.description
        val ingredients = _uiState.value.ingredients
        val steps = _uiState.value.steps
        val thumbnailImage = _uiState.value.thumbnailImage

        if (title.isBlank()) {
            _event.emit(RecipeRegisterEvent.ShowToast("제목을 입력하세요."))
            return false
        }
        if (description.isBlank()) {
            _event.emit(RecipeRegisterEvent.ShowToast("설명을 입력하세요."))
            return false
        }
        if (ingredients.isEmpty()) {
            _event.emit(RecipeRegisterEvent.ShowToast("하나 이상의 재료를 입력하세요."))
            return false
        }
        if (steps.isEmpty()) {
            _event.emit(RecipeRegisterEvent.ShowToast("하나 이상의 단계를 입력하세요."))
            return false
        }
        if (thumbnailImage == null) {
            _event.emit(RecipeRegisterEvent.ShowToast("대표 이미지를 설정하세요."))
            return false
        }

        return true
    }

    private fun convertToFile(uri: Uri): File? {
        val fileName = getFileName(uri) ?: return null
        val file = File(context.cacheDir, fileName)
        file.createNewFile()

        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return file
    }

    private fun getFileName(uri: Uri): String? {
        val contentResolver = context.contentResolver
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            Log.d("RecipeRegisterViewModel", "cursor.getString(nameIndex) : ${cursor.getString(nameIndex)}")
            return cursor.getString(nameIndex)
        }
        return null
    }

}