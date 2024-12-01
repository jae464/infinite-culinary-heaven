package com.jae464.presentation.register

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Step
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
    private val contestRepository: ContestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeRegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<RecipeRegisterEvent>()
    val event = _event.asSharedFlow()

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

    private fun submit() {
        viewModelScope.launch {
            if (!validateForm()) return@launch

            val thumbnailImageUri = _uiState.value.thumbnailImage?.toUri()
            var thumbnailImageFile: File? = null

            if (thumbnailImageUri != null) {
                thumbnailImageFile = convertToFile(thumbnailImageUri)
            }

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

            // todo Home에서 넘어올때 argument로 넘겨주는 방식으로 바꿀 필요 있음
            val currentContest = contestRepository.getCurrentContest().getOrNull() ?: return@launch

            _uiState.update { state -> state.copy(isRegistering = true) }

            recipeRepository.registerRecipe(
                images = imageFiles,
                thumbnailImage = thumbnailImageFile?.name,
                title = _uiState.value.title,
                description = _uiState.value.description,
                ingredients = _uiState.value.ingredients,
                steps = _uiState.value.steps.map { step ->
                    step.copy(
                        imageUrl = if (step.imageUrl != null) getFileName(
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
            return cursor.getString(nameIndex)
        }
        return null
    }


}